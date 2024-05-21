package net.devple.ledger.config.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import net.devple.ledger.config.properties.JwtProperties;
import net.devple.ledger.domain.entity.LedgerUser;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final String TOKEN_PREFIX ="Bearer ";
  private final JwtProperties PROPERTIES;
  private final Algorithm ALGORITHM;

  public JwtProvider(JwtProperties properties) {
    this.PROPERTIES = properties;
    this.ALGORITHM = Algorithm.HMAC512(properties.getSecret());
  }

  public String generateAccessToken(LedgerUser user) {
    JWTCreator.Builder builder = JWT.create().withSubject(user.getId().toString());
    return TOKEN_PREFIX + builder
        .withIssuer("devple-ledger")
        .withExpiresAt(Date.from(Instant.now().plusSeconds(PROPERTIES.getValidity())))
        .withClaim("userType",user.getRole().name())
        .sign(ALGORITHM);
  }

  public String generateRefreshToken(LedgerUser user) {
    JWTCreator.Builder builder = JWT.create().withSubject(user.getId().toString());
    return TOKEN_PREFIX + builder
        .withIssuer("devple-ledger-refresh")
        .withExpiresAt(Date.from(Instant.now().plusSeconds(PROPERTIES.getRefresh())))
        .withClaim("userType",user.getRole().name())
        .sign(ALGORITHM);
  }

  public String resolveToken(HttpServletRequest request) {
    String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isNotEmpty(headerToken) && headerToken.startsWith(TOKEN_PREFIX)){
      return headerToken.substring(7);
    }
    return null;
  }

  public DecodedJWT parseToken(String token) {
    return JWT.require(ALGORITHM).build().verify(token.replaceFirst(TOKEN_PREFIX,""));
  }


}
