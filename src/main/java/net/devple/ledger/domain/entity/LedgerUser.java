package net.devple.ledger.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.devple.ledger.domain.common.EntityAudit;
import net.devple.ledger.domain.entity.type.UserRole;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of="id", callSuper = false)
public class LedgerUser extends EntityAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "INT UNSIGNED")
  private Long id;
  private String loginId;
  private String password;
  private String email;
  private String nickname;
  private String phone;
  @Convert(converter = UserRole.Converter.class)
  private UserRole role;

  public static LedgerUserBuilder builder(){
    return new LedgerUserBuilder();
  }

  public static class LedgerUserBuilder{
    private final LedgerUser instance;

    private LedgerUserBuilder(){
      this.instance = new LedgerUser();
    }

    public LedgerUserBuilder id(Long id){
      this.instance.setId(id);
      return this;
    }

    public LedgerUserBuilder loginId(String loginId){
      this.instance.setLoginId(loginId);
      return this;
    }

    public LedgerUserBuilder password(String password) {
      this.instance.setPassword(password);
      return this;
    }

    public LedgerUserBuilder email(String email){
      this.instance.setEmail(email);
      return this;
    }

    public LedgerUserBuilder nickname(String nickname){
      this.instance.setNickname(nickname);
      return this;
    }

    public LedgerUserBuilder phone(String phone){
      this.instance.setPhone(phone);
      return this;
    }

    public LedgerUserBuilder role(UserRole role){
      this.instance.setRole(role);
      return this;
    }

    public LedgerUser build(){
      return this.instance;
    }

  }
}
