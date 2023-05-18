package za.co.firmdev.payroll.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import za.co.firmdev.payroll.data.models.UserAccount;

@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
}
