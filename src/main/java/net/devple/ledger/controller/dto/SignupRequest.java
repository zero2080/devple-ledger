package net.devple.ledger.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.devple.ledger.domain.entity.LedgerUser;
import net.devple.ledger.domain.entity.type.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SignupRequest {
  private String loginId;
  @Getter
  private String password;
  private String nickname;
  private String email;
  private String phone;

  public LedgerUser toEntity(PasswordEncoder encoder) {
    return LedgerUser.builder()
        .loginId(loginId)
        .nickname(nickname)
        .password(encoder.encode(password))
        .email(email)
        .phone(phone)
        .role(UserRole.USER)
        .build();
  }
}
