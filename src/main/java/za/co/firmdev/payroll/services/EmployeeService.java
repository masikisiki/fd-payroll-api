package za.co.firmdev.payroll.services;

import org.springframework.stereotype.Service;
import za.co.firmdev.payroll.data.models.Deduction;
import za.co.firmdev.payroll.data.models.Employee;
import za.co.firmdev.payroll.data.models.SalaryMap;
import za.co.firmdev.payroll.data.repository.DeductionRepository;
import za.co.firmdev.payroll.data.repository.EmployeeRepository;
import za.co.firmdev.payroll.data.repository.SalaryMapRepository;
import za.co.firmdev.payroll.dto.PayrollAddResponse;
import za.co.firmdev.payroll.enums.Deductions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SalaryMapRepository salaryMapRepository;

    private final DeductionRepository deductionRepository;
    private final SequenceService sequenceService;

    public EmployeeService(EmployeeRepository employeeRepository, SalaryMapRepository salaryMapRepository, DeductionRepository deductionRepository, SequenceService sequenceService) {
        this.employeeRepository = employeeRepository;
        this.salaryMapRepository = salaryMapRepository;
        this.deductionRepository = deductionRepository;
        this.sequenceService = sequenceService;
    }

    public Employee registerEmployee(Employee emp) {
        if (emp.getId() == null) emp.setId(sequenceService.nextEmpSequence());
        return employeeRepository.save(emp);
    }

    public PayrollAddResponse addToPayroll(Employee emp, SalaryMap salaryInfo, List<Deductions> deductions) {
        if (salaryInfo == null) throw new IllegalArgumentException("Salary object can't be null");
        if (salaryInfo.getCtcSalary() <= 0) {
            throw new IllegalArgumentException("Cannot salary of 0 to payroll");
        }
        if (salaryInfo.getEffectiveDate() == null) throw new IllegalArgumentException("Effective is required");
        if (emp == null) throw new IllegalArgumentException("Employee object can't be null");
        if (emp.getId() == null) emp.setId(sequenceService.nextEmpSequence());
        employeeRepository.save(emp);

        salaryInfo.setId(sequenceService.nextSalSequence());
        salaryInfo.setEmployeeId(emp.getId());
        salaryMapRepository.save(salaryInfo);

        var employeeDeductions = new HashSet<Deductions>();
        if (deductions != null) {
            deductions = new ArrayList<>(){{}};
        }
        employeeDeductions.addAll(deductions);

        var deds = new ArrayList<Deduction>();
        for (var d : deductions) {
            var deduction = new Deduction();
            deduction.setId(sequenceService.nextDeductSequence());
//            deduction.setEmployeeId(emp.getId());
            deduction.setName(d.name());
            deduction.setAmount(100);
            deds.add(deduction);
        }
        var persistedDeductions = deductionRepository.saveAll(deds);
        return new PayrollAddResponse(emp, salaryInfo, persistedDeductions);
    }
}
