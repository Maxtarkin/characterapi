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
public class Dexterity extends BaseAttribute {

  @Column(name = "sleight_of_hand_proficiency", columnDefinition = "boolean default false")
  private Boolean sleightOfHandProficiency;

  @Column(name = "acrobatics_proficiency", columnDefinition = "boolean default false")
  private Boolean acrobaticsProficiency;

  @Column(name = "stealth_proficiency", columnDefinition = "boolean default false")
  private Boolean stealthProficiency;
}
