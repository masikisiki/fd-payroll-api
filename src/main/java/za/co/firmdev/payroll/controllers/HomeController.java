package za.co.firmdev.payroll.controllers;


import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String home() {
        SecurityContext context = SecurityContextHolder.getContext();
        return "Welcome Home! here.."+context.getAuthentication().getName();
    }
}
