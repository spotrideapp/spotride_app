FROM eclipse-temurin:21
MAINTAINER SPOTRIDE
COPY build/libs/spotride-*-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]