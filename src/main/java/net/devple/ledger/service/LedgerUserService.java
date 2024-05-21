package net.devple.ledger.service;

import lombok.RequiredArgsConstructor;
import net.devple.ledger.controller.dto.SignupRequest;
import net.devple.ledger.repository.LedgerUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LedgerUserService {

  private final LedgerUserRepository repository;
  private final PasswordEncoder encoder;

  public void signup(SignupRequest body){
    repository.save(body.toEntity(encoder));
  }
}
