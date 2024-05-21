package net.devple.ledger.config.security.service;

import lombok.RequiredArgsConstructor;
import net.devple.ledger.config.security.model.LedgerUserDetails;
import net.devple.ledger.repository.LedgerUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LedgerUserDetailService implements UserDetailsService {

  private final LedgerUserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByLoginId(username)
        .map(LedgerUserDetails::new)
        .orElseThrow(()->new RuntimeException("User not found")); // TODO : 예외 처리 핸들러 적용시 수정 필요
  }
}

