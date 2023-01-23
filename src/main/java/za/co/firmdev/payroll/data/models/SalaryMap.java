package za.co.firmdev.payroll.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class SalaryMap {
    @Id
    private long id;
    private long employeeId;
    private int ctcSalary;

    private LocalDate effectiveDate;
}
