package za.co.firmdev.payroll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.firmdev.payroll.data.models.Deduction;
import za.co.firmdev.payroll.data.models.Employee;
import za.co.firmdev.payroll.data.models.SalaryMap;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayrollAddResponse {
    private Employee employee;
    private SalaryMap salaryMap;
    private List<Deduction> deductions;
}
