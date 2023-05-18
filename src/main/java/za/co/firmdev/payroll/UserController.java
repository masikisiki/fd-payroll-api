package za.co.firmdev.payroll;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.firmdev.payroll.data.models.UserAccount;
import za.co.firmdev.payroll.data.repository.UserAccountRepository;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserAccountRepository accountRepository;

    public UserController(UserAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/profile/{id}")
    public Optional<UserAccount> userProfile(@PathVariable("id") String profileId) {
        return accountRepository.findById(profileId);
    }
}
