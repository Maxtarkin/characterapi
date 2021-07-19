package ru.tarala.character.creator.data.entity;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.tarala.character.creator.utility.BaseEntityListener;

@Getter
@Setter
@Data
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id;

  @Version @Column private Long version;

  @Column(name = "create_date")
  private ZonedDateTime createDate;

  @Column(name = "update_date")
  private ZonedDateTime updateDate;

  @Column(length = 50, name = "creator_id")
  private UUID creatorId;

  @Column(length = 50, name = "updater_id")
  private UUID updaterId;
}
