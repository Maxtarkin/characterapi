package ru.tarala.character.creator.service;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tarala.character.creator.data.entity.character.CharacterModel;
import ru.tarala.character.creator.data.mapper.CharacterMapper;
import ru.tarala.character.creator.data.repository.character.CharacterRepository;
import ru.tarala.character.creator.model.CharacterCreateRequest;
import ru.tarala.character.creator.model.CharacterDetailInfo;
import ru.tarala.character.creator.model.CharacterUpdateRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterService {

  private final CharacterRepository characterRepository;
  private final CharacterMapper characterMapper;

  /**
   * Create a character
   *
   * @param characterCreateRequest essential data
   * @return created character identifier
   */
  @Transactional
  public Long createCharacter(CharacterCreateRequest characterCreateRequest) {
    //    log.info(String.format("Create the new decision: %s", decisionCreateRequest.toString()));
    CharacterModel characterModel =
        characterMapper.mapCharacterCreateRequestToCharacterModel(characterCreateRequest);
    characterModel
        .getAttributeBlock()
        .calculateAttributeBlockFields(characterCreateRequest.getLevel());
    characterModel = characterRepository.save(characterModel);
    return characterModel.getId();
  }

  /**
   * Get a character by id
   *
   * @param characterId an identifier of a desired character
   * @return CharacterDetailInfo detailed character information
   */
  public CharacterDetailInfo getCharacterDetailInfoById(Long characterId) {
    Optional<CharacterModel> optionalDecision = characterRepository.findById(characterId);
    if (optionalDecision.isPresent()) {
      CharacterModel characterModel = optionalDecision.get();
      return characterMapper.mapCharacterModelToCharacterDetailInfo(characterModel);
    } else {
      throw new EntityNotFoundException(
          String.format("Cannot find character with id %s", characterId));
    }
  }

  /**
   * Delete a character by id
   *
   * @param characterId an identifier of a deleting character
   * @return true if deleted successfully
   */
  public boolean deleteCharacterById(Long characterId) {
    Optional<CharacterModel> optionalCharacterModel = characterRepository.findById(characterId);
    if (optionalCharacterModel.isPresent()) {
      characterRepository.delete(optionalCharacterModel.get());
      return true;
    } else {
      throw new EntityNotFoundException(
          String.format("Cannot find character with id %s", characterId));
    }
  }

  /**
   * Patch a character by id
   *
   * @param characterId an identifier of a updating character
   * @param characterUpdateRequest data for update
   * @return true if updated successfully
   */
  public boolean updateCharacterById(
      Long characterId, CharacterUpdateRequest characterUpdateRequest) {
    Optional<CharacterModel> optionalCharacterModel = characterRepository.findById(characterId);
    if (optionalCharacterModel.isPresent()) {
      CharacterModel characterModel = optionalCharacterModel.get();
      characterMapper.updateCharacterModelFromCharacterUpdateRequest(
          characterModel, characterUpdateRequest);
      characterModel.getAttributeBlock().calculateAttributeBlockFields(characterModel.getLevel());
      characterRepository.save(characterModel);
      return true;
    } else {
      throw new EntityNotFoundException(
          String.format("Cannot find character with id %s", characterId));
    }
  }
}
