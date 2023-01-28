package za.co.firmdev.payroll.services;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.firmdev.payroll.data.EmployeeDeduction;
import za.co.firmdev.payroll.data.PayrollTransaction;
import za.co.firmdev.payroll.data.models.Deduction;
import za.co.firmdev.payroll.data.models.Employee;
import za.co.firmdev.payroll.data.models.SalaryMap;
import za.co.firmdev.payroll.data.repository.DeductionRepository;
import za.co.firmdev.payroll.data.repository.EmployeeDeductionRepository;
import za.co.firmdev.payroll.data.repository.EmployeeRepository;
import za.co.firmdev.payroll.data.repository.SalaryMapRepository;
import za.co.firmdev.payroll.enums.DeductionType;
import za.co.firmdev.payroll.enums.Deductions;
import za.co.firmdev.payroll.enums.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class PayrollRunServiceTests {


    @InjectMocks
    private PayrollRunService payrollRunService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DeductionRepository deductionRepository;
    @Mock
    private SalaryMapRepository salaryMapRepository;
    @Mock
    private EmployeeDeductionRepository employeeDeductionRepository;
    @Mock
    private SequenceService sequenceService;

    // step 0 - generate batch id

    //setp 1 - gross deductions

    //Step 2 - paycheck validation and processing

    //step 3 - taxes  and withheld payments processing
    @Test
    public void test() {
        //Arrange
        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(
                Optional.of(Employee.EmployeeBuilder.anEmployee()
                        .withId(1l)
                        .withFirstName("First Name 1")
                        .withLastName("Last Name 1")
                        .withEmail("user@mail.com")
                        .build()));
        Mockito.when(salaryMapRepository.findById(anyLong())).thenReturn(
                Optional.of(SalaryMap.SalaryMapBuilder.aSalaryMap()
                        .withId(1l)
                        .withCtcSalary(2500000)
                        .withEmployeeId(1l)
                        .withEffectiveDate(LocalDate.now())
                        .build()));
        Mockito.when(employeeDeductionRepository.findAllByEmployeeId(anyLong())).thenReturn(
                List.of(EmployeeDeduction.EmployeeDeductionBuilder.anEmployeeDeduction()
                                .withId(1l)
                                .withEmployeeId(1l)
                                .withDeductionId(1l)
                                .build(),
                        EmployeeDeduction.EmployeeDeductionBuilder.anEmployeeDeduction()
                                .withId(2l)
                                .withEmployeeId(1l)
                                .withDeductionId(2l)
                                .build()));
        Mockito.when(deductionRepository.findAllById(anyList())).thenReturn(
                List.of(Deduction.DeductionBuilder.aDeduction()
                                .withId(1l)
                                .withName(Deductions.TAX.name())
                                .withDescription("Government Tax")
                                .withType(DeductionType.RATE_PERCENT_ON_SALARY)
                                .withRatePercent(15)
                                .withAccount("INCOME_TAX")
                                .build(),
                        Deduction.DeductionBuilder.aDeduction()
                                .withId(2l)
                                .withName(Deductions.UIF.name())
                                .withDescription("DISCOVERY MED AID")
                                .withType(DeductionType.RATE_PERCENT_ON_SALARY)
                                .withRatePercent(1)
                                .withAccount("UIF")
                                .build()
                ));
        //Act
        var transactions = payrollRunService.runForEmployee(1l);

        assertThat(transactions).isNotNull();
        assertThat(transactions.size()).isEqualTo(4);
        var debitCreditGroup = transactions.stream().collect(Collectors.groupingBy(PayrollTransaction::getType));
        assertThat(debitCreditGroup.keySet().stream().toList()).containsAll(List.of(TransactionType.CREDIT, TransactionType.DEBIT));
        var debitTotal = debitCreditGroup.get(TransactionType.DEBIT).stream().mapToInt(PayrollTransaction::getAmount).sum();
        var creditTotal = debitCreditGroup.get(TransactionType.CREDIT).stream().mapToInt(PayrollTransaction::getAmount).sum();
        assertThat(debitTotal).isEqualTo(creditTotal);
        assertThat(transactions)
                .as("Expected all transaction to have destination account")
                .allMatch(a -> StringUtils.isNotBlank(a.getAccount()));
        assertThat(transactions)
                .as("Expected all transactions have a batch Id")
                .allMatch(a -> StringUtils.isNotBlank(a.getBatchId()));

    }
}
