package net.devple.ledger.domain.common;

import jakarta.persistence.AttributeConverter;
import java.util.Arrays;

public interface CodebaseEnum<T> {
  int getCode();

  abstract class AbstractConverter<E extends Enum<E> & CodebaseEnum<E>> implements
      AttributeConverter<E, Integer> {

    @Override
    public Integer convertToDatabaseColumn(E attribute) {
      return attribute == null ? null : attribute.getCode();
    }

    public E convertToEnum(Class<E> clazz, Integer dbData) {
      if (dbData == null) {
        return null;
      }
      return Arrays.stream(clazz.getEnumConstants())
          .filter(e -> e.getCode() == dbData).findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Invalid Enum Code"));
    }
  }
}
