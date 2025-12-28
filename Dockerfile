FROM maven:3.9-eclipse-temurin-25 AS build

RUN apt-get update && apt-get install -y git && rm -rf /var/lib/apt/lists/*

WORKDIR /app

RUN git clone https://github.com/MarianoFrancisco/sprintforge-common.git
WORKDIR /app/sprintforge-common/common
RUN mvn -DskipTests install

WORKDIR /app
COPY employee/pom.xml .
COPY employee/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar"]
