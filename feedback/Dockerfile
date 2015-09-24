FROM java:8-jre

ENV jar feedback-0.3.0-SNAPSHOT.jar

COPY build/libs/$jar /app/
WORKDIR /app

RUN echo $jar

RUN echo "java -jar $jar" > ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

EXPOSE 8080

CMD ./entrypoint.sh