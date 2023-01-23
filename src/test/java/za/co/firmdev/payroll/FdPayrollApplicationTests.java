package za.co.firmdev.payroll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import za.co.firmdev.payroll.data.repository.DeductionRepository;
import za.co.firmdev.payroll.services.SequenceService;


@DataMongoTest()
@Import(SequenceService.class)
class FdPayrollApplicationTests extends AbstractEmbededMongoDbTest {

    @Autowired
    private DeductionRepository employeeRepository;

    private SequenceService seq;

    @Test
    void contextLoads() {
        employeeRepository.findAll();
    }

}
