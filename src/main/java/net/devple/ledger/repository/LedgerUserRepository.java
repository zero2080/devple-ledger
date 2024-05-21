package net.devple.ledger.repository;

import io.micrometer.observation.ObservationFilter;
import java.util.Optional;
import net.devple.ledger.domain.entity.LedgerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerUserRepository extends JpaRepository<LedgerUser, Long>{

  Optional<LedgerUser> findByLoginId(String username);
}
