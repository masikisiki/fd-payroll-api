package za.co.firmdev.payroll;

import org.springframework.web.bind.annotation.*;
import za.co.firmdev.payroll.data.EmployeeDeduction;
import za.co.firmdev.payroll.dto.PayrollAddRequest;
import za.co.firmdev.payroll.dto.PayrollAddResponse;
import za.co.firmdev.payroll.enums.Deductions;
import za.co.firmdev.payroll.services.DeductionsService;
import za.co.firmdev.payroll.services.EmployeeService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {
    private final EmployeeService employeeService;
    private final DeductionsService deductionsService;

    public PayrollController(EmployeeService employeeService, DeductionsService deductionsService) {
        this.employeeService = employeeService;
        this.deductionsService = deductionsService;
    }

    @PostMapping("/add")
    public PayrollAddResponse addToPayroll(@RequestBody PayrollAddRequest request) {
        return this.employeeService.addToPayroll(request.getEmployee(), request.getSalaryMap(), request.getDeductions());
    }

    @PostMapping("/employee-deduction/map/{employeeId}")
    public List<EmployeeDeduction> addToPayroll(@PathVariable long employeeId, @RequestBody Deductions[] deductions) {
        return deductionsService.map(employeeId, Arrays.asList(deductions));
    }
}
