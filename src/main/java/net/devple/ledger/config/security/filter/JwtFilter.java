package net.devple.ledger.config.security.filter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import net.devple.ledger.config.security.provider.JwtProvider;
import net.devple.ledger.repository.LedgerUserRepository;
import net.devple.ledger.config.security.model.LedgerUserDetails;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

  private final LedgerUserRepository repository;
  private final JwtProvider tokenProvider;

  @Override
  protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String jwt = tokenProvider.resolveToken(request);

    if(StringUtils.isNotEmpty(jwt)){

      Long subject = Long.parseLong(
          tokenProvider.parseToken(jwt).getSubject()
      );

      repository.findById(subject)
          .map(LedgerUserDetails::new)
          .map(detail->new UsernamePasswordAuthenticationToken(detail.getUser(),null,detail.getAuthorities()))
          .ifPresent(auth->{
            auth.setDetails(authenticationDetailsSource.buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
          });
    }

    filterChain.doFilter(request,response); // 작동중
    SecurityContextHolder.clearContext();
  }
}
