package ru.tarala.character.creator.data.repository.character;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.tarala.character.creator.data.entity.character.CharacterModel;

public interface CharacterRepository extends PagingAndSortingRepository<CharacterModel, Long> {}
