package ru.tarala.character.creator.data.entity.character;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import ru.tarala.character.creator.data.entity.BaseEntity;

@Data
@Entity
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"attributeBlock"})
@Table(schema = "character")
@DynamicUpdate
public class CharacterModel extends BaseEntity {

  @Column(length = 64)
  private String name;

  @Column private Integer level;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "attribute_block_id", referencedColumnName = "id")
  private AttributeBlock attributeBlock;
}
