package ru.tarala.character.creator.data.entity.character;

import java.util.Collections;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import ru.tarala.character.creator.data.entity.BaseEntity;
import ru.tarala.character.creator.data.entity.character.attribute.BaseAttribute;
import ru.tarala.character.creator.data.entity.character.attribute.Charisma;
import ru.tarala.character.creator.data.entity.character.attribute.Constitution;
import ru.tarala.character.creator.data.entity.character.attribute.Dexterity;
import ru.tarala.character.creator.data.entity.character.attribute.Intelligence;
import ru.tarala.character.creator.data.entity.character.attribute.Strength;
import ru.tarala.character.creator.data.entity.character.attribute.Wisdom;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(schema = "character")
@DynamicUpdate
public class AttributeBlock extends BaseEntity {

  private static int PASSIVE_PERCEPTION_BASE_VALUE = 10;

  /*@Id
  @Column(unique = true, nullable = false)
  private Long id;*/

  @Column
  @Setter(value = AccessLevel.NONE)
  private Integer proficiencyBonus;

  @Column
  @Setter(value = AccessLevel.NONE)
  private Integer passivePerception;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "strength_id", referencedColumnName = "id")
  private Strength strength;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "dexterity_id", referencedColumnName = "id")
  private Dexterity dexterity;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "constitution_id", referencedColumnName = "id")
  private Constitution constitution;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "intelligence_id", referencedColumnName = "id")
  private Intelligence intelligence;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "wisdom_id", referencedColumnName = "id")
  private Wisdom wisdom;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "charisma_id", referencedColumnName = "id")
  private Charisma charisma;

  /**
   * Get unmodifiable set of attributeBlock
   *
   * @return Set<BasicAttribute>
   */
  public Set<BaseAttribute> getAttributeSet() {
    return Collections.unmodifiableSet(
        Set.of(strength, dexterity, constitution, intelligence, wisdom, charisma));
  }

  public void calculateAttributeBlockFields(Integer level) {
    calculateAttributeModifiers();
    calculateProficiencyBonus(level);
    calculatePassivePerception();
  }

  private void calculateAttributeModifiers() {
    getAttributeSet().forEach(BaseAttribute::calculateModifier);
  }

  private void calculatePassivePerception() {
    passivePerception =
        wisdom.getPerceptionProficiency()
            ? PASSIVE_PERCEPTION_BASE_VALUE + wisdom.getModifier() + proficiencyBonus
            : PASSIVE_PERCEPTION_BASE_VALUE + wisdom.getModifier();
  }

  private void calculateProficiencyBonus(Integer level) {
    switch (level) {
      case 1:
      case 2:
      case 3:
      case 4:
        proficiencyBonus = 2;
        break;
      case 5:
      case 6:
      case 7:
      case 8:
        proficiencyBonus = 3;
        break;
      case 9:
      case 10:
      case 11:
      case 12:
        proficiencyBonus = 4;
        break;
      case 13:
      case 14:
      case 15:
      case 16:
        proficiencyBonus = 5;
        break;
      case 17:
      case 18:
      case 19:
      case 20:
        proficiencyBonus = 6;
        break;
      default:
        throw new IllegalArgumentException(String.format("Wrong level: %s", level));
    }
  }
}
