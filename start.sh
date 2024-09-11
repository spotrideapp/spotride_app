./gradlew clean build &&
docker build --tag=spotride:latest . &&
docker-compose up