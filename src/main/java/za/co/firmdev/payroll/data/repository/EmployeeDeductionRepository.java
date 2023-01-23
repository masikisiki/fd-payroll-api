package za.co.firmdev.payroll.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.co.firmdev.payroll.data.EmployeeDeduction;

public interface EmployeeDeductionRepository extends MongoRepository<EmployeeDeduction, Long> {


}
