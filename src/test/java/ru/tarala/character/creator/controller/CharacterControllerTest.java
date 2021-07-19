package ru.tarala.character.creator.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.tarala.character.creator.BaseTest;
import ru.tarala.character.creator.data.entity.character.CharacterModel;
import ru.tarala.character.creator.data.repository.character.CharacterRepository;
import ru.tarala.character.creator.model.AttributeBlockCreate;
import ru.tarala.character.creator.model.AttributeBlockUpdate;
import ru.tarala.character.creator.model.BaseIdResponse;
import ru.tarala.character.creator.model.CharacterCreateRequest;
import ru.tarala.character.creator.model.CharacterDetailInfoResponse;
import ru.tarala.character.creator.model.CharacterUpdateRequest;
import ru.tarala.character.creator.model.CharismaAttributeCreate;
import ru.tarala.character.creator.model.ConstitutionAttributeCreate;
import ru.tarala.character.creator.model.DexterityAttributeCreate;
import ru.tarala.character.creator.model.DexterityAttributeUpdate;
import ru.tarala.character.creator.model.IntelligenceAttributeCreate;
import ru.tarala.character.creator.model.StrengthAttributeCreate;
import ru.tarala.character.creator.model.WisdomAttributeCreate;
import ru.tarala.character.creator.model.WisdomAttributeUpdate;

class CharacterControllerTest extends BaseTest {

  private final String BASE_URL = "/api/1.0.0/character/%s";

  @Autowired private CharacterRepository characterRepository;

  @Nested
  class CreateCharacter {

    @Test
    @Transactional
    void createCharacter_CorrectRequest_Success() throws Exception {
      CharacterCreateRequest request = getCharacterCreateRequest();
      MvcResult result =
          mockMvc
              .perform(
                  post(String.format(BASE_URL, ""))
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(convertToString(request)))
              .andDo(print())
              .andExpect(status().isCreated())
              .andReturn();
      BaseIdResponse idResponse =
          parseObject(BaseIdResponse.class, result.getResponse().getContentAsString());
      Long createdCharacterId = idResponse.getId();
      Optional<CharacterModel> optionalCharacterModel =
          characterRepository.findById(createdCharacterId);
      assertTrue(optionalCharacterModel.isPresent());
      CharacterModelControllerTestUtility.assertCreatedCharacterIsCorrect(
          request, optionalCharacterModel.get());
    }

    @Test
    void createCharacter_NullBodyRequest_HttpMessageNotReadableException() throws Exception {
      mockMvc
          .perform(
              post(String.format(BASE_URL, ""))
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(convertToString(null)))
          .andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(
              result ->
                  assertTrue(
                      result.getResolvedException() instanceof HttpMessageNotReadableException));
    }

    @Test
    void createCharacter_IncorrectRequest_MethodArgumentNotValidException() throws Exception {
      var createRequest = new CharacterCreateRequest();
      mockMvc
          .perform(
              post(String.format(BASE_URL, ""))
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(convertToString(createRequest)))
          .andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(
              result ->
                  assertTrue(
                      result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    private CharacterCreateRequest getCharacterCreateRequest() {
      return new CharacterCreateRequest()
          .name("Test Create Character")
          .level(5)
          .attributeBlock(
              new AttributeBlockCreate()
                  .strength(new StrengthAttributeCreate().currentValue(10))
                  .dexterity(new DexterityAttributeCreate().currentValue(15))
                  .constitution(new ConstitutionAttributeCreate().currentValue(8))
                  .intelligence(new IntelligenceAttributeCreate().currentValue(12))
                  .wisdom(new WisdomAttributeCreate().currentValue(15))
                  .charisma(new CharismaAttributeCreate().currentValue(11)));
    }
  }

  @Nested
  class getCharacterDetailInfoById {

    @Test
    @Transactional
    void getCharacterDetailInfoById_CorrectId_Success() throws Exception {
      Long characterId = 1L;
      MvcResult result =
          mockMvc
              .perform(get(String.format(BASE_URL, characterId)))
              .andDo(print())
              .andExpect(status().isOk())
              .andReturn();
      CharacterDetailInfoResponse response =
          parseObject(CharacterDetailInfoResponse.class, result.getResponse().getContentAsString());
      Optional<CharacterModel> optionalCharacterModel = characterRepository.findById(characterId);
      assertTrue(optionalCharacterModel.isPresent());
      CharacterModelControllerTestUtility.assertCharacterDetailInfoIsCorrect(
          optionalCharacterModel.get(), response.getData());
    }

    @Test
    void getCharacterDetailInfoById_CorrectId_EntityNotFoundException() throws Exception {
      Long characterId = 1001L;
      assertFalse(characterRepository.existsById(characterId));
      mockMvc
          .perform(get(String.format(BASE_URL, characterId)))
          .andDo(print())
          .andExpect(status().isNotFound())
          .andExpect(
              result ->
                  assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void getCharacterDetailInfoById_IncorrectId_MethodArgumentTypeMismatchException()
        throws Exception {
      mockMvc
          .perform(get(String.format(BASE_URL, "abc")))
          .andDo(print())
          .andExpect(status().is4xxClientError())
          .andExpect(
              result ->
                  assertTrue(
                      result.getResolvedException()
                          instanceof MethodArgumentTypeMismatchException));
    }
  }

  @Nested
  class deleteCharacterById {

    @Test
    @Transactional
    void deleteCharacterById_CorrectId_Success() throws Exception {
      Long characterId = 1L;
      assertTrue(characterRepository.existsById(characterId));
      mockMvc
          .perform(delete(String.format(BASE_URL, characterId)))
          .andDo(print())
          .andExpect(status().isOk());
      assertFalse(characterRepository.existsById(characterId));
    }

    @Test
    void deleteCharacterById_CorrectId_EntityNotFoundException() throws Exception {
      Long characterId = 1001L;
      assertFalse(characterRepository.existsById(characterId));
      mockMvc
          .perform(delete(String.format(BASE_URL, characterId)))
          .andDo(print())
          .andExpect(status().isNotFound())
          .andExpect(
              result ->
                  assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void deleteCharacterById_IncorrectId_MethodArgumentTypeMismatchException() throws Exception {
      mockMvc
          .perform(delete(String.format(BASE_URL, "abc")))
          .andDo(print())
          .andExpect(status().is4xxClientError())
          .andExpect(
              result ->
                  assertTrue(
                      result.getResolvedException()
                          instanceof MethodArgumentTypeMismatchException));
    }
  }

  @Nested
  class updateCharacterById {

    @Test
    @Transactional
    void updateCharacterById_CorrectRequest_Success() throws Exception {
      Long characterId = 1L;
      assertTrue(characterRepository.existsById(characterId));
      CharacterUpdateRequest updateRequest = getCharacterUpdateRequest();
      MvcResult result =
          mockMvc
              .perform(
                  patch(String.format(BASE_URL, characterId))
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(convertToString(updateRequest)))
              .andDo(print())
              .andExpect(status().isOk())
              .andReturn();
      BaseIdResponse idResponse =
          parseObject(BaseIdResponse.class, result.getResponse().getContentAsString());
      Long createdCharacterId = idResponse.getId();
      Optional<CharacterModel> optionalCharacterModel =
          characterRepository.findById(createdCharacterId);
      assertTrue(optionalCharacterModel.isPresent());
      CharacterModelControllerTestUtility.assertUpdatedCharacterIsCorrect(
          updateRequest, optionalCharacterModel.get());
    }

    @Test
    void updateCharacterById_InCorrectRequest_EntityNotFoundException() throws Exception {
      Long characterId = 1001L;
      assertFalse(characterRepository.existsById(characterId));
      CharacterUpdateRequest request = getCharacterUpdateRequest();
      mockMvc
          .perform(
              patch(String.format(BASE_URL, characterId))
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(convertToString(request)))
          .andDo(print())
          .andExpect(status().isNotFound())
          .andExpect(
              result ->
                  assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void updateCharacterById_InCorrectRequest_MethodArgumentNotValidException() throws Exception {
      Long characterId = 1L;
      assertTrue(characterRepository.existsById(characterId));
      CharacterUpdateRequest incorrectUpdateRequest = getIncorrectCharacterUpdateRequest();
      mockMvc
          .perform(
              patch(String.format(BASE_URL, characterId))
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(convertToString(incorrectUpdateRequest)))
          .andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(
              result ->
                  assertTrue(
                      result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    private CharacterUpdateRequest getCharacterUpdateRequest() {
      return new CharacterUpdateRequest()
          .name("Experienced")
          .level(5)
          .attributeBlock(
              new AttributeBlockUpdate()
                  .dexterity(
                      new DexterityAttributeUpdate()
                          .currentValue(15)
                          .sleightOfHandProficiency(true)
                          .stealthProficiency(true))
                  .wisdom(
                      new WisdomAttributeUpdate()
                          .currentValue(15)
                          .proficiency(true)
                          .perceptionProficiency(true)));
    }

    private CharacterUpdateRequest getIncorrectCharacterUpdateRequest() {
      return new CharacterUpdateRequest()
          .name("Experienced")
          .level(35)
          .attributeBlock(
              new AttributeBlockUpdate()
                  .dexterity(
                      new DexterityAttributeUpdate()
                          .currentValue(15)
                          .sleightOfHandProficiency(true)
                          .stealthProficiency(true))
                  .wisdom(
                      new WisdomAttributeUpdate()
                          .currentValue(15)
                          .proficiency(true)
                          .perceptionProficiency(true)));
    }
  }
}
