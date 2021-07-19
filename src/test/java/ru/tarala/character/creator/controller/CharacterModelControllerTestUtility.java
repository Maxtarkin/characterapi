package ru.tarala.character.creator.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ru.tarala.character.creator.data.entity.character.CharacterModel;
import ru.tarala.character.creator.data.entity.character.attribute.Charisma;
import ru.tarala.character.creator.data.entity.character.attribute.Constitution;
import ru.tarala.character.creator.data.entity.character.attribute.Dexterity;
import ru.tarala.character.creator.data.entity.character.attribute.Intelligence;
import ru.tarala.character.creator.data.entity.character.attribute.Strength;
import ru.tarala.character.creator.data.entity.character.attribute.Wisdom;
import ru.tarala.character.creator.model.CharacterCreateRequest;
import ru.tarala.character.creator.model.CharacterDetailInfo;
import ru.tarala.character.creator.model.CharacterUpdateRequest;
import ru.tarala.character.creator.model.CharismaAttributeCreate;
import ru.tarala.character.creator.model.CharismaAttributeInfo;
import ru.tarala.character.creator.model.ConstitutionAttributeCreate;
import ru.tarala.character.creator.model.ConstitutionAttributeInfo;
import ru.tarala.character.creator.model.DexterityAttributeCreate;
import ru.tarala.character.creator.model.DexterityAttributeInfo;
import ru.tarala.character.creator.model.IntelligenceAttributeCreate;
import ru.tarala.character.creator.model.IntelligenceAttributeInfo;
import ru.tarala.character.creator.model.StrengthAttributeCreate;
import ru.tarala.character.creator.model.StrengthAttributeInfo;
import ru.tarala.character.creator.model.WisdomAttributeCreate;
import ru.tarala.character.creator.model.WisdomAttributeInfo;

public class CharacterModelControllerTestUtility {

  private static final Integer PROFICIENCY_BONUS_AFTER_UPDATE = 3;
  private static final Integer BASIC_PASSIVE_PERCEPTION = 10;
  private static final Integer DEXTERITY_MODIFIER_AFTER_UPDATE = 2;
  private static final Integer WISDOM_MODIFIER_AFTER_UPDATE = 2;
  private static final Integer STRENGTH_MODIFIER_AFTER_UPDATE = 0;
  private static final Integer PASSIVE_PERCEPTION_AFTER_UPDATE =
      BASIC_PASSIVE_PERCEPTION + PROFICIENCY_BONUS_AFTER_UPDATE + WISDOM_MODIFIER_AFTER_UPDATE;

  public static void assertCreatedCharacterIsCorrect(
      CharacterCreateRequest expected, CharacterModel real) {
    assertEquals(expected.getName(), real.getName());
    assertEquals(expected.getLevel(), real.getLevel());
    assertCreatedStrengthIsCorrect(
        expected.getAttributeBlock().getStrength(), real.getAttributeBlock().getStrength());
    assertCreatedDexterityIsCorrect(
        expected.getAttributeBlock().getDexterity(), real.getAttributeBlock().getDexterity());
    assertCreatedConstitutionIsCorrect(
        expected.getAttributeBlock().getConstitution(), real.getAttributeBlock().getConstitution());
    assertCreatedIntelligenceIsCorrect(
        expected.getAttributeBlock().getIntelligence(), real.getAttributeBlock().getIntelligence());
    assertCreatedWisdomIsCorrect(
        expected.getAttributeBlock().getWisdom(), real.getAttributeBlock().getWisdom());
    assertCreatedCharismaIsCorrect(
        expected.getAttributeBlock().getCharisma(), real.getAttributeBlock().getCharisma());
  }

  public static void assertCharacterDetailInfoIsCorrect(
      CharacterModel expected, CharacterDetailInfo real) {
    assertEquals(expected.getName(), real.getName());
    assertEquals(expected.getLevel(), real.getLevel());
    assertEquals(
        expected.getAttributeBlock().getProficiencyBonus(),
        real.getAttributeBlockInfo().getProficiencyBonus());
    assertEquals(
        expected.getAttributeBlock().getPassivePerception(),
        real.getAttributeBlockInfo().getPassivePerception());
    assertStrengthInfoIsCorrect(
        expected.getAttributeBlock().getStrength(), real.getAttributeBlockInfo().getStrength());
    assertDexterityInfoIsCorrect(
        expected.getAttributeBlock().getDexterity(), real.getAttributeBlockInfo().getDexterity());
    assertConstitutionInfoIsCorrect(
        expected.getAttributeBlock().getConstitution(), real.getAttributeBlockInfo().getConstitution());
    assertIntelligenceInfoIsCorrect(
        expected.getAttributeBlock().getIntelligence(), real.getAttributeBlockInfo().getIntelligence());
    assertWisdomInfoIsCorrect(
        expected.getAttributeBlock().getWisdom(), real.getAttributeBlockInfo().getWisdom());
    assertCharismaInfoIsCorrect(
        expected.getAttributeBlock().getCharisma(), real.getAttributeBlockInfo().getCharisma());
  }

  public static void assertUpdatedCharacterIsCorrect(
      CharacterUpdateRequest expected, CharacterModel real) {
    assertEquals(expected.getName(), real.getName());
    assertEquals(expected.getLevel(), real.getLevel());
    assertEquals(PROFICIENCY_BONUS_AFTER_UPDATE, real.getAttributeBlock().getProficiencyBonus());
    assertEquals(PASSIVE_PERCEPTION_AFTER_UPDATE, real.getAttributeBlock().getPassivePerception());
    assertEquals(STRENGTH_MODIFIER_AFTER_UPDATE, real.getAttributeBlock().getStrength().getModifier());
    assertEquals(
        expected.getAttributeBlock().getDexterity().getCurrentValue(),
        real.getAttributeBlock().getDexterity().getCurrentValue());
    assertEquals(
        expected.getAttributeBlock().getDexterity().getStealthProficiency(),
        real.getAttributeBlock().getDexterity().getStealthProficiency());
    assertEquals(
        expected.getAttributeBlock().getDexterity().getSleightOfHandProficiency(),
        real.getAttributeBlock().getDexterity().getSleightOfHandProficiency());
    assertEquals(
        DEXTERITY_MODIFIER_AFTER_UPDATE, real.getAttributeBlock().getDexterity().getModifier());
    assertFalse(real.getAttributeBlock().getDexterity().getProficiency());
    assertFalse(real.getAttributeBlock().getDexterity().getAcrobaticsProficiency());
    assertEquals(
        expected.getAttributeBlock().getWisdom().getProficiency(),
        real.getAttributeBlock().getWisdom().getProficiency());
    assertEquals(
        expected.getAttributeBlock().getWisdom().getPerceptionProficiency(),
        real.getAttributeBlock().getWisdom().getPerceptionProficiency());
    assertEquals(WISDOM_MODIFIER_AFTER_UPDATE, real.getAttributeBlock().getDexterity().getModifier());
    assertFalse(real.getAttributeBlock().getWisdom().getInsightProficiency());
    assertFalse(real.getAttributeBlock().getWisdom().getMedicineProficiency());
    assertFalse(real.getAttributeBlock().getWisdom().getSurvivalProficiency());
    assertFalse(real.getAttributeBlock().getWisdom().getAnimalHandlingProficiency());
  }

  private static void assertCreatedStrengthIsCorrect(
      StrengthAttributeCreate expected, Strength real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getAthleticsProficiency(), real.getAthleticsProficiency());
  }

  private static void assertCreatedDexterityIsCorrect(
      DexterityAttributeCreate expected, Dexterity real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getAcrobaticsProficiency(), real.getAcrobaticsProficiency());
    assertEquals(expected.getSleightOfHandProficiency(), real.getSleightOfHandProficiency());
    assertEquals(expected.getStealthProficiency(), real.getStealthProficiency());
  }

  private static void assertCreatedConstitutionIsCorrect(
      ConstitutionAttributeCreate expected, Constitution real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
  }

  private static void assertCreatedIntelligenceIsCorrect(
      IntelligenceAttributeCreate expected, Intelligence real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getArcaneProficiency(), real.getArcaneProficiency());
    assertEquals(expected.getHistoryProficiency(), real.getHistoryProficiency());
    assertEquals(expected.getInvestigationProficiency(), real.getInvestigationProficiency());
    assertEquals(expected.getNatureProficiency(), real.getNatureProficiency());
    assertEquals(expected.getReligionProficiency(), real.getReligionProficiency());
  }

  private static void assertCreatedWisdomIsCorrect(WisdomAttributeCreate expected, Wisdom real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getAnimalHandlingProficiency(), real.getAnimalHandlingProficiency());
    assertEquals(expected.getInsightProficiency(), real.getInsightProficiency());
    assertEquals(expected.getMedicineProficiency(), real.getMedicineProficiency());
    assertEquals(expected.getPerceptionProficiency(), real.getPerceptionProficiency());
    assertEquals(expected.getSurvivalProficiency(), real.getSurvivalProficiency());
  }

  private static void assertCreatedCharismaIsCorrect(
      CharismaAttributeCreate expected, Charisma real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getDeceptionProficiency(), real.getDeceptionProficiency());
    assertEquals(expected.getIntimidationProficiency(), real.getIntimidationProficiency());
    assertEquals(expected.getPerformanceProficiency(), real.getPerformanceProficiency());
    assertEquals(expected.getPersuasionProficiency(), real.getPersuasionProficiency());
  }

  private static void assertStrengthInfoIsCorrect(Strength expected, StrengthAttributeInfo real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getModifier(), real.getModifier());
    assertEquals(expected.getAthleticsProficiency(), real.getAthleticsProficiency());
  }

  private static void assertDexterityInfoIsCorrect(
      Dexterity expected, DexterityAttributeInfo real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getModifier(), real.getModifier());
    assertEquals(expected.getAcrobaticsProficiency(), real.getAcrobaticsProficiency());
    assertEquals(expected.getSleightOfHandProficiency(), real.getSleightOfHandProficiency());
    assertEquals(expected.getStealthProficiency(), real.getStealthProficiency());
  }

  private static void assertConstitutionInfoIsCorrect(
      Constitution expected, @NotNull @Valid ConstitutionAttributeInfo real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getModifier(), real.getModifier());
  }

  private static void assertIntelligenceInfoIsCorrect(
      Intelligence expected, IntelligenceAttributeInfo real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getModifier(), real.getModifier());
    assertEquals(expected.getArcaneProficiency(), real.getArcaneProficiency());
    assertEquals(expected.getHistoryProficiency(), real.getHistoryProficiency());
    assertEquals(expected.getInvestigationProficiency(), real.getInvestigationProficiency());
    assertEquals(expected.getNatureProficiency(), real.getNatureProficiency());
    assertEquals(expected.getReligionProficiency(), real.getReligionProficiency());
  }

  private static void assertWisdomInfoIsCorrect(Wisdom expected, WisdomAttributeInfo real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getModifier(), real.getModifier());
    assertEquals(expected.getAnimalHandlingProficiency(), real.getAnimalHandlingProficiency());
    assertEquals(expected.getInsightProficiency(), real.getInsightProficiency());
    assertEquals(expected.getMedicineProficiency(), real.getMedicineProficiency());
    assertEquals(expected.getPerceptionProficiency(), real.getPerceptionProficiency());
    assertEquals(expected.getSurvivalProficiency(), real.getSurvivalProficiency());
  }

  private static void assertCharismaInfoIsCorrect(Charisma expected, CharismaAttributeInfo real) {
    assertEquals(expected.getCurrentValue(), real.getCurrentValue());
    assertEquals(expected.getProficiency(), real.getProficiency());
    assertEquals(expected.getModifier(), real.getModifier());
    assertEquals(expected.getDeceptionProficiency(), real.getDeceptionProficiency());
    assertEquals(expected.getIntimidationProficiency(), real.getIntimidationProficiency());
    assertEquals(expected.getPerformanceProficiency(), real.getPerformanceProficiency());
    assertEquals(expected.getPersuasionProficiency(), real.getPersuasionProficiency());
  }
}
