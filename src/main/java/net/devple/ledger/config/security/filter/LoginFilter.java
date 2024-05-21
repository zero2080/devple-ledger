package net.devple.ledger.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.devple.ledger.config.security.handler.AuthenticationHandler;
import net.devple.ledger.config.security.model.dto.UserLoginRequest;
import net.devple.ledger.config.security.provider.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper;

  public LoginFilter( AuthenticationHandler handler, AuthenticationProvider provider, ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    setAuthenticationSuccessHandler(handler);
    setAuthenticationFailureHandler(handler);
    setAuthenticationManager(provider::authenticate);
    setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/authenticate", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    UserLoginRequest loginRequest;
    try{
      loginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);
    }catch(Exception e){
      return null;
    }

    String loginId = loginRequest.loginId();
    String password = loginRequest.password();

    if(loginId == null){
      loginId = "";
    }

    if(password == null){
      password = "";
    }

    // email validation 원하면 해보세요!!

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginId, password);

    setDetails(request, authRequest);

    return getAuthenticationManager().authenticate(authRequest);
  }
}