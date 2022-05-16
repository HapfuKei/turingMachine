FROM azul/zulu-openjdk-alpine:17.0.1
VOLUME /turing-machine
ADD build/libs/discret.math-0.0.1-SNAPSHOT.jar turing-machine.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/turing-machine.jar","-Pvaadin.productionMode=ture"]