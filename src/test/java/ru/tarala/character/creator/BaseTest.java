package ru.tarala.character.creator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = BaseTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public abstract class BaseTest {

  @Autowired protected MockMvc mockMvc;

  /*  @Autowired
  protected SelectService selectService;*/

  @Autowired protected ObjectMapper objectMapper;

  protected String convertToString(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  protected <T> T parseObject(Class<T> resultClass, String jsonObject)
      throws JsonProcessingException {
    return objectMapper.readValue(jsonObject, resultClass);
  }

  public static PostgreSQLContainer<?> postgreDBContainer =
      new PostgreSQLContainer<>("postgres:9.4");

  static {
    postgreDBContainer.start();
  }

  public static class DockerPostgreDataSourceInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + postgreDBContainer.getJdbcUrl(),
          "spring.datasource.username=" + postgreDBContainer.getUsername(),
          "spring.datasource.password=" + postgreDBContainer.getPassword());
    }
  }
}
