package net.devple.ledger.controller.dto;

import net.devple.ledger.domain.entity.LedgerUser;
import net.devple.ledger.domain.entity.type.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignupRequest(String loginId, String password,String nickname,String email,String phone) {
  public LedgerUser toEntity(PasswordEncoder encoder) {
    return LedgerUser.builder()
        .loginId(loginId)
        .nickname(nickname)
        .password(encoder.encode(password))
        .email(email)
        .phone(phone)
        .role(UserRole.ROLE_USER)
        .build();
  }
}
