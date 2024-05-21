package net.devple.ledger.domain.entity.type;


import net.devple.ledger.domain.common.CodebaseEnum;

public enum UserRole implements CodebaseEnum<UserRole> {
  ROLE_ADMIN(1),
  ROLE_USER(2);

  private final int code;

  UserRole(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }

  @jakarta.persistence.Converter
  public static class Converter extends CodebaseEnum.AbstractConverter<UserRole> {
    @Override
    public UserRole convertToEntityAttribute(Integer dbData) {
      return super.convertToEnum(UserRole.class, dbData);
    }
  }
}
