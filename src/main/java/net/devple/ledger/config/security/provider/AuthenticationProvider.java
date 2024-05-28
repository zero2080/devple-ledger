package net.devple.ledger.config.security.provider;

import net.devple.ledger.config.security.model.LedgerUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends DaoAuthenticationProvider {
  public AuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    super(passwordEncoder);
    setUserDetailsService(userDetailsService);
  }

  @Override
  protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
    LedgerUserDetails userDetails = (LedgerUserDetails) user;
    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authResult.setDetails(authentication.getDetails());
    return authResult;
  }
}
