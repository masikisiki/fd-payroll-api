package za.co.firmdev.payroll.data.listeners;

import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveCallback;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;
import za.co.firmdev.payroll.data.models.Employee;
import za.co.firmdev.payroll.services.SequenceService;

@Order(1)
@Component
public class OnBeforeSave implements BeforeSaveCallback<Employee>, AfterSaveCallback<Employee>, Ordered {

    private final String mongoDbUri;

    public OnBeforeSave(@Value("${spring.data.mongodb.uri}") String mongoDbUri) {
        this.mongoDbUri = mongoDbUri;
    }

    @Override
    public Employee onBeforeSave(Employee entity, Document document, String collection) {
        var mongo = new MongoTemplate(MongoClients.create(mongoDbUri), "database_sequences");
        entity.setId(SequenceService.next(SequenceService.EMPLOYEE_SEQUENCE, mongo));
        return entity;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Employee onAfterSave(Employee entity, Document document, String collection) {
        return entity;
    }
}
