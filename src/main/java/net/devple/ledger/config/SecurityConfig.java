package net.devple.ledger.config;


import net.devple.ledger.config.properties.JwtProperties;
import net.devple.ledger.config.security.filter.JwtFilter;
import net.devple.ledger.config.security.filter.LoginFilter;
import net.devple.ledger.config.security.handler.AuthenticationHandler;
import net.devple.ledger.config.security.model.LedgerUserDetails;
import net.devple.ledger.repository.LedgerUserRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  protected SecurityFilterChain configure(HttpSecurity http, LoginFilter loginFilter, JwtFilter jwtFilter, AuthenticationHandler entryPoint) throws Exception {
    return http
        .csrf(CsrfConfigurer::disable)
        .exceptionHandling(handler->handler.authenticationEntryPoint(entryPoint))
        .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성!!
        .authorizeHttpRequests(req ->
            req.requestMatchers(HttpMethod.POST,"/user","/user/authenticate").anonymous()
                .anyRequest().authenticated()
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
  public UserDetailsService userDetailsService(LedgerUserRepository repository) {
    return username->repository.findByLoginId(username)
        .map(LedgerUserDetails::new)
        .orElseThrow(()->new UsernameNotFoundException("User not found"));
  }

  @Bean
  @Profile("!prod")
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/test/**");
  }

}
