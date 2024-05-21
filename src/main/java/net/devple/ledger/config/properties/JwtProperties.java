package net.devple.ledger.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {
  private String secret;
  private int validity;
  private int refresh;
}
