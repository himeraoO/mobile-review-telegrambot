FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=mrtb_bot
ENV BOT_TOKEN=5351991825:AAEs5qhCZZvw7fPpwIQA7wCv4SUIOdSZn4E
ENV BOT_DB_USERNAME=mrtb_db_user
ENV BOT_DB_PASSWORD=mrtb_db_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]
