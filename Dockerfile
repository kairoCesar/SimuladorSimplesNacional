FROM openjdk:21-jdk
COPY target/SimuladorSimplesNacional-0.0.1-SNAPSHOT.jar /app/simulador.jar

CMD ["java", "-jar", "/app/simulador.jar"]