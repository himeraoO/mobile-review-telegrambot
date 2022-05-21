FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=mobile_review_bot
ENV BOT_TOKEN=5351991825:AAEs5qhCZZvw7fPpwIQA7wCv4SUIOdSZn4E
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]