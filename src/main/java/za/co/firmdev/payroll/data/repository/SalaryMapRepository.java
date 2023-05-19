package za.co.firmdev.payroll.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import za.co.firmdev.payroll.data.models.SalaryMap;

//@RepositoryRestResource(collectionResourceRel = "salarymap", path = "salarymap")
public interface SalaryMapRepository extends MongoRepository<SalaryMap, Long> {
}
