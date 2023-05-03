FROM openjdk:11-ea-11-jdk-slim
WORKDIR /app/helmantic-users

COPY ./pom.xml /app
COPY ./helmantic-users/.mvn ./.mvn
COPY ./helmantic-users/mvnw .
COPY ./helmantic-users/pom.xml .

#ESTE COMANDO SOLO HA SIDO NECESARIO PARA CONVERTIR EL FORMATO DEL FICHERO MVNW DE WINDOWS A LINUX, SINO FALLABA
RUN sed -i -e 's/\r$//' ./mvnw

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./helmantic-users/src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/helmantic-users-0.0.1-SNAPSHOT.jar"]