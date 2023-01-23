package za.co.firmdev.payroll.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Employee {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
