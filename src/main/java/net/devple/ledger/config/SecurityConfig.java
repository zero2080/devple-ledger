package net.devple.ledger.config;


import net.devple.ledger.config.properties.JwtProperties;
import net.devple.ledger.config.security.filter.JwtFilter;
import net.devple.ledger.config.security.filter.LoginFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  protected SecurityFilterChain configure(HttpSecurity http, LoginFilter loginFilter, JwtFilter jwtFilter) throws Exception {
    return http
        .csrf(CsrfConfigurer::disable)
        .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성!!
        .authorizeHttpRequests(req ->
            req.anyRequest().authenticated()
        )
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .rememberMe(AbstractHttpConfigurer::disable)
            .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(HttpMethod.POST, "/user","/user/authenticate");
  }

}
