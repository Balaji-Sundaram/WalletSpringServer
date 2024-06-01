# Stage 1: Build the application
FROM amazoncorretto:17-alpine-jdk AS builder
WORKDIR /app
COPY . .
RUN gradle build
RUN #apk add --no-cache openjdk17 openjdk17-jre gradle
# Stage 2: Runner image
FROM amazoncorretto:17-alpine-jdk
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8090
CMD ["java", "-jar", "app.jar"]
#MD ["/usr/bin/java", "17"]


#FROM alpine:3.17
#RUN apk add --no-cache &&\
#            wget -O /etc/apk/keys/amazoncorretto.rsa.pub https://apk.corretto.aws/amazoncorretto.rsa.pub && \
#            echo "https://apk.corretto.aws" >> /etc/apk/repositories && \
#            apk update &&\
#            apk add amazon-corretto-17