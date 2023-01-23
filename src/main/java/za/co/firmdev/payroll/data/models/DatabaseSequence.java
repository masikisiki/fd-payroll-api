package za.co.firmdev.payroll.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@Data
public class DatabaseSequence {
    @Id
    private String id;
    private long seq;
}