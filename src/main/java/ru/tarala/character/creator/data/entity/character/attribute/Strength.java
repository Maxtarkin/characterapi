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
public class Strength extends BaseAttribute {

  @Column(name = "athletics_proficiency", columnDefinition = "boolean default false")
  private Boolean athleticsProficiency;
}
