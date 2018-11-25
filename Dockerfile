FROM java:8u111-jre-alpine


ADD /target/pap.jar app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]