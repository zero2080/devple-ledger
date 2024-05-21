package net.devple.ledger.config.security.provider;

import net.devple.ledger.config.security.model.LedgerUserDetails;
import net.devple.ledger.config.security.service.LedgerUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends DaoAuthenticationProvider {
  public AuthenticationProvider(LedgerUserDetailService userDetailsService, PasswordEncoder passwordEncoder) {
    super();
    setUserDetailsService(userDetailsService);
    setPasswordEncoder(passwordEncoder);
  }

  @Override
  protected Authentication createSuccessAuthentication(Object principal,
      Authentication authentication, UserDetails user) {
    LedgerUserDetails userDetails = (LedgerUserDetails) user;
    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authResult.setDetails(authentication.getDetails());
    return authResult;
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    try{
      super.additionalAuthenticationChecks(userDetails, authentication);
    } catch (Exception e) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
  }
}
