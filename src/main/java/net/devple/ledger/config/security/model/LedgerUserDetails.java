package net.devple.ledger.config.security.model;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import net.devple.ledger.domain.entity.LedgerUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class LedgerUserDetails extends User {

  private LedgerUser user;

  public LedgerUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public LedgerUserDetails(LedgerUser user) {
    super(
        user.getLoginId(),      // 회원 ID
        user.getPassword(),   // 비밀번호
        true,                 // 계정 활성화 여부 ( true : 활성화 )
        true,                 // 계정 만료 여부 ( true : 만료되지 않음 )
        true,                 // 비밀번호 만료 여부 ( true : 만료되지 않음 )
        true,                 // 계정 잠금 ( true : 잠기지 않음 )
        Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
    );
    this.user = user;
  }
}
