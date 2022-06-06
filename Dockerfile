FROM openjdk

VOLUME /tmp

ADD target/webpos-0.0.1-SNAPSHOT.jar webpos-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/webpos-0.0.1-SNAPSHOT.jar"]