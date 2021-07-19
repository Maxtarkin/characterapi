package ru.tarala.character.creator.data.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.tarala.character.creator.data.entity.character.CharacterModel;
import ru.tarala.character.creator.model.CharacterCreateRequest;
import ru.tarala.character.creator.model.CharacterDetailInfo;
import ru.tarala.character.creator.model.CharacterUpdateRequest;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

//  @Mapping(target = "attributeBlock", source = "attributeBlock")
  CharacterModel mapCharacterCreateRequestToCharacterModel(
      CharacterCreateRequest characterCreateRequest);

  @Mapping(target = "attributeBlockInfo", source = "characterModel.attributeBlock")
  CharacterDetailInfo mapCharacterModelToCharacterDetailInfo(CharacterModel characterModel);

  @BeanMapping(
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
  void updateCharacterModelFromCharacterUpdateRequest(
      @MappingTarget CharacterModel characterModel, CharacterUpdateRequest characterUpdateRequest);
}
