FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /coupon
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /coupon
COPY --from=build /coupon/target/*.jar app.jar
EXPOSE 8080 5005
ENTRYPOINT ["java","-jar","app.jar"]