package letsit_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/home", "/login/**", "/static/**", "/index.html", "/assets/**", "/vite.svg", "/posts/**", "/favicon.ico", "/error").permitAll() // 정적 파일 및 특정 경로 허용
                                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                /*
                .oauth2Login(oauth2Login -> oauth2Login
                        .clientRegistrationRepository(clientRegistrationRepository)
                        .authorizedClientService(authorizedClientService)
                        .loginPage("/login/")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )

                 */

                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/home", true)
                                .permitAll()
                )


                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 필요에 따라 CSRF 보호 비활성화
                //.formLogin(formLogin -> formLogin.disable()); // 기본 로그인 폼 비활성화

        return http.build();
    }
}