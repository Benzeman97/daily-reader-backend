FROM openjdk:11

COPY ./build/libs/daily-reader-service.jar daily-reader-service.jar
#COPY entrypoint.sh /usr/local/bin/entrypoint.sh

EXPOSE 8089
CMD ["java","-jar","daily-reader-service.jar"]

#RUN chmod +x /usr/local/bin/entrypoint.sh
#CMD bash -c 'source /usr/local/bin/entrypoint.sh && java -Djava.security.egd=file:/dev/./urandom -jar /daily-reader-service.jar'