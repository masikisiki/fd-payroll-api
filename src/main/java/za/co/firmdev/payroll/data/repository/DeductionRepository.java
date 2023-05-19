package za.co.firmdev.payroll.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import za.co.firmdev.payroll.data.models.Deduction;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "deduction", path = "deduction")
public interface DeductionRepository extends MongoRepository<Deduction, Long> {
    boolean existsAllByNameIn(List<String> names);

    List<Deduction> findAllByNameIn(List<String> names);
}


