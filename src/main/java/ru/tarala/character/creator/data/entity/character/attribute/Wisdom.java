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
public class Wisdom extends BaseAttribute {

  @Column(name = "animal_handling_proficiency", columnDefinition = "boolean default false")
  private Boolean animalHandlingProficiency;

  @Column(name = "insight_proficiency", columnDefinition = "boolean default false")
  private Boolean insightProficiency;

  @Column(name = "medicine_proficiency", columnDefinition = "boolean default false")
  private Boolean medicineProficiency;

  @Column(name = "perception_proficiency", columnDefinition = "boolean default false")
  private Boolean perceptionProficiency;

  @Column(name = "survival_proficiency", columnDefinition = "boolean default false")
  private Boolean survivalProficiency;
}
