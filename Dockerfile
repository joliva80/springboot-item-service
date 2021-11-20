FROM azul/zulu-openjdk:17
VOLUME /tmp
ADD ./target/springboot-item-service-0.0.1-SNAPSHOT.jar item-service.jar
ENTRYPOINT ["java","-jar","/item-service.jar"]