FROM openjdk:11
VOLUME /tmp
EXPOSE 8002
ADD ./target/springboot-item-service-0.0.1-SNAPSHOT.jar service-item.jar
ENTRYPOINT ["java","-jar","/service-item.jar"]