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
public class Charisma extends BaseAttribute {

  @Column(name = "deception_proficiency", columnDefinition = "boolean default false")
  private Boolean deceptionProficiency;

  @Column(name = "intimidation_proficiency", columnDefinition = "boolean default false")
  private Boolean intimidationProficiency;

  @Column(name = "performance_proficiency", columnDefinition = "boolean default false")
  private Boolean performanceProficiency;

  @Column(name = "persuasion_proficiency", columnDefinition = "boolean default false")
  private Boolean persuasionProficiency;
}
