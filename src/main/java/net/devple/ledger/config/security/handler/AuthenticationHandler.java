package net.devple.ledger.config.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import net.devple.ledger.config.security.model.dto.AuthenticateResponse;
import net.devple.ledger.config.security.model.LedgerUserDetails;
import net.devple.ledger.config.security.provider.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler implements AuthenticationSuccessHandler,
    AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;
  private final JwtProvider tokenProvider;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
      // 인증 실패
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) throws IOException, ServletException {
      LedgerUserDetails principal = (LedgerUserDetails) authentication.getPrincipal();

      //JWT 생성
      String accessToken = tokenProvider.generateAccessToken(principal.getUser());
      String refreshToken = tokenProvider.generateRefreshToken(principal.getUser());

      AuthenticateResponse result = new AuthenticateResponse(accessToken,refreshToken);

      response.setStatus(HttpStatus.OK.value());
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());

      try(PrintWriter writer = response.getWriter()){
          writer.write(objectMapper.writeValueAsString(result));
          writer.flush();
      }
  }
}
