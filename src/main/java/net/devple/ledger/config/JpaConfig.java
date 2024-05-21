package net.devple.ledger.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import net.devple.ledger.domain.entity.LedgerUser;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
@EnableSpringDataWebSupport
@EntityScan({"net.devple.ledger.domain"})
public class JpaConfig {
  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public AuditorAware<String> auditAware() {
    return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(auth -> {
          if (auth.getPrincipal() instanceof LedgerUser user) {
            return user.getId().toString();
          }
          return auth.getName();
        });
  }
}
