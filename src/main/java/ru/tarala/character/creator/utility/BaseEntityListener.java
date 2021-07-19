package ru.tarala.character.creator.utility;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.springframework.stereotype.Component;
import ru.tarala.character.creator.data.entity.BaseEntity;

@Component
public class BaseEntityListener {

  private static final UUID DEFAULT_CREATOR = UUID.randomUUID();

  @PrePersist
  public void beforeCreate(BaseEntity baseEntity) {
    if (baseEntity.getCreateDate() == null) {
      baseEntity.setCreateDate(ZonedDateTime.now());
    }
    baseEntity.setCreatorId(DEFAULT_CREATOR);
  }

  @PreUpdate
  public void beforeUpdate(BaseEntity baseEntity) {
    baseEntity.setUpdateDate(ZonedDateTime.now());
  }
}
