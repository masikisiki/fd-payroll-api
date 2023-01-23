package za.co.firmdev.payroll.dto;

import lombok.Data;
import za.co.firmdev.payroll.data.models.Employee;
import za.co.firmdev.payroll.data.models.SalaryMap;
import za.co.firmdev.payroll.enums.Deductions;

import java.util.List;

@Data
public class PayrollAddRequest {
    private Employee employee;
    private SalaryMap salaryMap;
    private List<Deductions> deductions;
}
