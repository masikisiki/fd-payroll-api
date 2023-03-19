package za.co.firmdev.payroll.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login/oauth2/code")
public class OAuthClientLogin {

    @GetMapping("/github")
    public Object github(Object client){
        return client;
    }

    @GetMapping("/google")
    public Object google(Object client){
        return client;
    }
}
