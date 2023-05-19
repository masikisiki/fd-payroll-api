package za.co.firmdev.payroll.data.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class UserAccount {
    @Id
    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;
    @Transient
    private String accessToken;
}
