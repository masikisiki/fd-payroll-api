package za.co.firmdev.payroll.services;


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
import za.co.firmdev.payroll.exceptions.DeductionAmountDeterminationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PayrollRunService {

    private final EmployeeRepository employeeRepository;
    private final DeductionRepository deductionRepository;
    private final EmployeeDeductionRepository employeeDeductionRepository;
    private final SalaryMapRepository salaryMapRepository;
    private final SequenceService sequenceService;
    private final List<PayrollTransaction> transactions = new ArrayList<>();
    private Employee employee;
    private List<Deduction> employeeDeductions;
    private String batchId;
    private SalaryMap employeeSalary;

    public PayrollRunService(EmployeeRepository employeeRepository, DeductionRepository deductionRepository, EmployeeDeductionRepository employeeDeductionRepository, SalaryMapRepository salaryMapRepository, SequenceService sequenceService) {
        this.employeeRepository = employeeRepository;
        this.deductionRepository = deductionRepository;
        this.employeeDeductionRepository = employeeDeductionRepository;
        this.salaryMapRepository = salaryMapRepository;
        this.sequenceService = sequenceService;
    }

    public List<PayrollTransaction> runForEmployee(long employeeId) {
        this.employee = employeeRepository.findById(employeeId).orElse(null);
        this.employeeSalary = salaryMapRepository.findById(employeeId).orElse(null);
        var deductionMap = employeeDeductionRepository.findAllByEmployeeId(employeeId);
        this.employeeDeductions = deductionRepository.findAllById(deductionMap.stream().map(EmployeeDeduction::getDeductionId).collect(Collectors.toList()));
        this.employeeDeductions = new ArrayList<>(employeeDeductions);
        if (employeeDeductions.stream().anyMatch(d -> d.getName().equals(Deductions.TAX.name()))) {
            var item = employeeDeductions.stream().filter(a -> a.getName().equals(Deductions.UIF.name())).findAny();
            item.ifPresent(deduction -> employeeDeductions.remove(deduction));
            employeeDeductions.add(Deduction.DeductionBuilder.aDeduction()
                    .withName(Deductions.UIF.name())
                    .withDescription(Deductions.UIF.name())
                    .withType(DeductionType.AMOUNT_ON_SALARY)
                    .withAmount(17712)
                    .withAccount("UIF")
                    .build());
        }
        //Step: 0
        this.prepareBatchId();
        //Step: 1
        runGrossAndDeductions();

        return transactions;
    }

    private void runGrossAndDeductions() {
        this.prepareBatchId();
        var grossSalaryIndicatorTransaction = PayrollTransaction.PayrollTransactionBuilder
                .aPayrollTransaction()
                .withId(sequenceService.nextPayrollSequence())
                .withBatchId(this.batchId)
                .withTargetReference1(employee.getId())
                .withTargetReference1Desc(String.format("%s %s", employee.getFirstName(), employee.getLastName()))
                .withAmount(this.employeeSalary.getCtcSalary())
                .withAccount("EMP_GROSS_PAY")
                .withType(TransactionType.DEBIT)
                .withDateTime(LocalDateTime.now())
                .build();

        this.transactions.add(grossSalaryIndicatorTransaction);

        var deductionTransactions = new ArrayList<PayrollTransaction>();
        for (Deduction employeeDeduction : employeeDeductions) {
            var deductionTransaction = PayrollTransaction.PayrollTransactionBuilder
                    .aPayrollTransaction()
                    .withId(sequenceService.nextPayrollSequence())
                    .withBatchId(this.batchId)
                    .withTargetReference1(employee.getId())
                    .withTargetReference1Desc(String.format("%s %s", employee.getFirstName(), employee.getLastName()))
                    .withAmount(deductionAmount(employeeDeduction, employeeSalary))
                    .withAccount(employeeDeduction.getAccount())
                    .withType(TransactionType.CREDIT)
                    .withDateTime(LocalDateTime.now())
                    .build();
            deductionTransactions.add(deductionTransaction);
        }
        this.transactions.addAll(deductionTransactions);

        var totalDeductionAmount = deductionTransactions
                .stream()
                .mapToInt(PayrollTransaction::getAmount)
                .sum();

        var netPayTransaction = PayrollTransaction.PayrollTransactionBuilder.aPayrollTransaction()
                .withId(sequenceService.nextPayrollSequence())
                .withBatchId(this.batchId)
                .withTargetReference1(employee.getId())
                .withTargetReference1Desc(String.format("%s %s", employee.getFirstName(), employee.getLastName()))
                .withAmount(employeeSalary.getCtcSalary() - totalDeductionAmount)
                .withAccount("NET_PAY")
                .withType(TransactionType.CREDIT)
                .withDateTime(LocalDateTime.now())
                .build();

        this.transactions.add(netPayTransaction);
    }

    private int deductionAmount(Deduction employeeDeduction, SalaryMap employeeSalary) {
        if (employeeDeduction.getType().equals(DeductionType.AMOUNT_ON_SALARY)) {
            return employeeDeduction.getAmount();
        }
        if (employeeDeduction.getType().equals(DeductionType.RATE_PERCENT_ON_SALARY)) {
            return (int) (employeeSalary.getCtcSalary() * (employeeDeduction.getRatePercent() / 100.0));
        }

        throw new DeductionAmountDeterminationException(employeeSalary.getEmployeeId());
    }

    private void prepareBatchId() {
        this.batchId = UUID.randomUUID().toString();
    }

    public void commit() {

    }
}
