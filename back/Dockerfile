FROM amazoncorretto:22-alpine3.19-jdk as builder
COPY . /usr/app
WORKDIR /usr/app
RUN apk add maven
RUN mvn package
RUN cp target/*.jar app.jar

FROM amazoncorretto:22-alpine3.19
WORKDIR /usr/app
COPY --from=builder /usr/app/app.jar app.jar
CMD [ "java", "-jar", "/usr/app/app.jar" ]