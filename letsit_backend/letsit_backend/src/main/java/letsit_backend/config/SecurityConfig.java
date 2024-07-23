package letsit_backend.config;

//import org.apache.catalina.filters.CorsFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Slf4j
@Configuration
public class SecurityConfig {

    //private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/home", "/login/**", "/static/**", "/index.html", "/assets/**", "/vite.svg", "/posts/**", "/favicon.ico", "/error", "/apply/**", "/projects/**", "/login/oauth2/callback/kakao").permitAll() // 정적 파일 및 특정 경로 허용
                                //.anyRequest().authenticated() // 나머지 요청은 인증 필요
                                .anyRequest().authenticated()
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
                                //.defaultSuccessUrl("/", true)
                                .permitAll()
                )


                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                );
                //.csrf(csrf -> csrf.disable()); // 필요에 따라 CSRF 보호 비활성화
                //.formLogin(formLogin -> formLogin.disable()); // 기본 로그인 폼 비활성화

        return http.build();
    }


    /*
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173"); // 프론트엔드 도메인으로 변경
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

     */



    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", configuration);

        log.info("Configured CORS with allowed origins: {}", configuration.getAllowedOrigins());

        return source;
    }
}