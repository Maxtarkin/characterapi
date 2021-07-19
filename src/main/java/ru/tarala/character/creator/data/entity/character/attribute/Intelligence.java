package ru.tarala.character.creator.data.entity.character.attribute;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@EqualsAndHashCode(
    callSuper = true,
    exclude = {})
@Table(schema = "character")
@DynamicUpdate
public class Intelligence extends BaseAttribute {

  @Column(name = "arcane_proficiency", columnDefinition = "boolean default false")
  private Boolean arcaneProficiency;

  @Column(name = "history_proficiency", columnDefinition = "boolean default false")
  private Boolean historyProficiency;

  @Column(name = "investigation_proficiency", columnDefinition = "boolean default false")
  private Boolean investigationProficiency;

  @Column(name = "nature_proficiency", columnDefinition = "boolean default false")
  private Boolean natureProficiency;

  @Column(name = "religion_proficiency", columnDefinition = "boolean default false")
  private Boolean religionProficiency;
}
