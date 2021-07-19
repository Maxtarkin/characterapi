package ru.tarala.character.creator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class CharacterCreatorApp {

  public static void main(String[] args) {
    SpringApplication.run(CharacterCreatorApp.class, args);
  }
}
