package za.co.firmdev.payroll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import za.co.firmdev.payroll.data.models.UserAccountBuilder;
import za.co.firmdev.payroll.data.repository.UserAccountRepository;

@Configuration
public class SecurityConfig {
    private static final String GOOGLE = "google";
    private static final String GITHUB = "github";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler loginSuccessHandler) throws Exception {
        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api-docs", "/api-docs/swagger-config"
                                , "/swagger-ui.html"
                                , "/v3/**"
                                , "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .oauth2Login().successHandler(loginSuccessHandler);
        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler onLoginSuccessHandler(UserAccountRepository accountRepository) {
        return (request, response, authentication) -> {
            var user = (DefaultOAuth2User) authentication.getPrincipal();
            if (user != null) {
                if (authentication instanceof OAuth2AuthenticationToken) {
                    var registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
                    var userAccount = switch (registrationId) {
                        case GOOGLE -> UserAccountBuilder.anUserAccount().buildWithGoogleOauthUser(user);
                        case GITHUB -> UserAccountBuilder.anUserAccount().buildWithGitHubOauthUser(user);
                        default -> throw new IllegalArgumentException("Invalid registrationId");
                    };
                    if (!accountRepository.existsById(userAccount.getId())) {
                        accountRepository.save(userAccount);
                    }
                    new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile/" + userAccount.getId());
                }
            } else {
                throw new RuntimeException("Could not retrieve user details");
            }
        };
    }
}
