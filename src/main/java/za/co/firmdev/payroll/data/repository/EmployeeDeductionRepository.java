package za.co.firmdev.payroll.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.co.firmdev.payroll.data.EmployeeDeduction;

import java.util.List;

public interface EmployeeDeductionRepository extends MongoRepository<EmployeeDeduction, Long> {

    List<EmployeeDeduction> findAllByEmployeeId(long employeeId);
}
