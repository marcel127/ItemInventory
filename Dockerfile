	FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Marcel Binyaminov
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean 
RUN mvn install 
RUN mvn package
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/ItemInventory-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "ItemInventory-0.0.1-SNAPSHOT.jar"]