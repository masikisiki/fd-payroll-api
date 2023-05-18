package za.co.firmdev.payroll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.firmdev.payroll.data.models.Employee;
import za.co.firmdev.payroll.data.models.SalaryMap;
import za.co.firmdev.payroll.data.repository.DeductionRepository;
import za.co.firmdev.payroll.data.repository.EmployeeRepository;
import za.co.firmdev.payroll.data.repository.SalaryMapRepository;
import za.co.firmdev.payroll.dto.PayrollAddResponse;
import za.co.firmdev.payroll.enums.Deductions;
import za.co.firmdev.payroll.services.EmployeeService;
import za.co.firmdev.payroll.services.SequenceService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    SalaryMapRepository salaryMapRepository;
    @Mock
    DeductionRepository deductionRepository;
    @Mock
    SequenceService sequenceService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerEmployee() {
        //Arrange
        var emp = new Employee();
        emp.setId(1l);
        emp.setFirstName("First Name 1");
        emp.setLastName("Last Name 1");
        emp.setEmail("emp@maill.com");
        when(employeeRepository.save(emp)).thenReturn(emp);
        //Act
        var result = employeeService.registerEmployee(emp);
        //Assert
        verify(employeeRepository, times(1)).save(any(Employee.class));
        assertThat(result).isNotNull();
    }

    @Test
    void addToPayroll() {
        //Arrange
        var emp = new Employee();
        emp.setId(1l);
        emp.setFirstName("First Name 1");
        emp.setLastName("Last Name 1");
        emp.setEmail("emp@maill.com");
        var salaryInfo = new SalaryMap();
        salaryInfo.setCtcSalary(30000);
        salaryInfo.setEffectiveDate(LocalDate.now());
        var deductions = List.of(Deductions.DISCOVERY_KEYCARE_MED);
        when(employeeRepository.save(emp)).thenReturn(emp);

        //Act
        var result = employeeService.addToPayroll(emp, salaryInfo, deductions);
        //Assert
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(salaryMapRepository, times(1)).save(any(SalaryMap.class));
        verify(deductionRepository, times(1)).saveAll(any(List.class));

//        verify(deductionRepository, times(1)).saveAll(any);


        assertThat(result).isNotNull();
        assertThat(result).extracting(PayrollAddResponse::getEmployee).isNotNull();
        assertThat(result).extracting(PayrollAddResponse::getSalaryMap).isNotNull();
        assertThat(result).extracting(PayrollAddResponse::getDeductions).isNotNull();

        assertThat(result.getSalaryMap().getCtcSalary()).isEqualTo(30000);
    }

}
