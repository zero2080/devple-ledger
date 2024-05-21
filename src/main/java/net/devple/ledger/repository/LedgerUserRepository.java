package net.devple.ledger.repository;

import net.devple.ledger.domain.entity.LedgerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerUserRepository extends JpaRepository<LedgerUser, Long>{

}
