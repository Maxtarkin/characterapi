package ru.tarala.character.creator.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tarala.character.creator.api.CharacterApi;
import ru.tarala.character.creator.model.BaseIdResponse;
import ru.tarala.character.creator.model.BaseResponse;
import ru.tarala.character.creator.model.CharacterCreateRequest;
import ru.tarala.character.creator.model.CharacterDetailInfo;
import ru.tarala.character.creator.model.CharacterDetailInfoResponse;
import ru.tarala.character.creator.model.CharacterUpdateRequest;
import ru.tarala.character.creator.service.CharacterService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1.0.0/")
@Slf4j
public class CharacterController implements CharacterApi {

  private final CharacterService characterService;

  @Override
  public ResponseEntity<BaseIdResponse> createCharacter(
      @Valid CharacterCreateRequest characterCreateRequest) {
    var response = new BaseIdResponse();
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(response.id(characterService.createCharacter(characterCreateRequest)));
  }

  @Override
  public ResponseEntity<CharacterDetailInfoResponse> getCharacterDetailInfoById(Long characterId) {
    CharacterDetailInfo characterDetailInfo =
        characterService.getCharacterDetailInfoById(characterId);
    var response = new CharacterDetailInfoResponse();
    return ResponseEntity.ok().body(response.data(characterDetailInfo));
  }

  @Override
  public ResponseEntity<BaseResponse> deleteCharacterById(Long characterId) {
    var response = new BaseResponse();
    response.setSuccess(characterService.deleteCharacterById(characterId));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Override
  public ResponseEntity<BaseIdResponse> updateCharacterById(
      Long characterId, @Valid CharacterUpdateRequest characterUpdateRequest) {
    var response = new BaseIdResponse();
    response.setSuccess(characterService.updateCharacterById(characterId, characterUpdateRequest));
    return ResponseEntity.status(HttpStatus.OK).body(response.id(characterId));
  }
}
