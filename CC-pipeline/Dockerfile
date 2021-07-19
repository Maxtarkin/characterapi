FROM maven:3.8.1-jdk-11 AS MAVEN_BUILD

COPY . .

RUN mvn clean package

FROM jdk-11

COPY --from=MAVEN_BULD /target/character-creator-1.0.0-SNAPSHOT.jar /character-creator.jar

EXPOSE 8080

CMD ["java", "-jar", "/tmp/character-creator.jar"]
