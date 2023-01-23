package za.co.firmdev.payroll.services;

import org.springframework.stereotype.Service;
import za.co.firmdev.payroll.data.EmployeeDeduction;
import za.co.firmdev.payroll.data.models.Deduction;
import za.co.firmdev.payroll.data.repository.DeductionRepository;
import za.co.firmdev.payroll.data.repository.EmployeeDeductionRepository;
import za.co.firmdev.payroll.data.repository.EmployeeRepository;
import za.co.firmdev.payroll.enums.Deductions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeductionsService {

    private final EmployeeRepository employeeRepository;
    private final DeductionRepository deductionRepository;

    private final EmployeeDeductionRepository employeeDeductionRepository;

    private final SequenceService sequenceService;

    public DeductionsService(EmployeeRepository employeeRepository, DeductionRepository deductionRepository, EmployeeDeductionRepository employeeDeductionRepository, SequenceService sequenceService) {
        this.employeeRepository = employeeRepository;
        this.deductionRepository = deductionRepository;
        this.employeeDeductionRepository = employeeDeductionRepository;
        this.sequenceService = sequenceService;
    }


    public ArrayList<EmployeeDeduction> map(long employeeId, List<Deductions> deductionNames) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException(employeeId);
        }

        var names = deductionNames.stream().map(Enum::name).collect(Collectors.toList());
        if (!deductionRepository.existsAllByNameIn(names)) {
            throw new EntityNotFoundException(names);
        }

        List<Deduction> allMatchDeductions = deductionRepository.findAllByNameIn(names);
        var createdDeduction = new ArrayList<EmployeeDeduction>();
        for (Deduction deduction : allMatchDeductions) {
            var empDeduction = EmployeeDeduction.EmployeeDeductionBuilder
                    .anEmployeeDeduction()
                    .withId(sequenceService.nexEmpDeductionSequence())
                    .withEmployeeId(employeeId)
                    .withDeductionId(deduction.getId())
                    .build();
            var ded = employeeDeductionRepository.save(empDeduction);
            createdDeduction.add(ded);
        }
        return createdDeduction;
    }
}
