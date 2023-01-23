package za.co.firmdev.payroll.data.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import za.co.firmdev.payroll.AbstractEmbededMongoDbTest;
import za.co.firmdev.payroll.data.models.Employee;

import static org.assertj.core.api.Java6Assertions.assertThat;


@DataMongoTest()
class EmployeeRepositoryTest extends AbstractEmbededMongoDbTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    public void shouldCreate() {
        var employee = new Employee();
        employee.setId(1l);
        employee.setFirstName("Emp Name 1");
        employee.setLastName("Emp Surname 1");
        employee.setEmail("emp@mail1.com");

        employeeRepository.save(employee);

        assertThat(employeeRepository.findById(employee.getId())).isNotNull();
    }
}