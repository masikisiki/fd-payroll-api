package za.co.firmdev.payroll.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import za.co.firmdev.payroll.data.models.Employee;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")
public interface EmployeeRepository extends MongoRepository<Employee, Long> {
}
