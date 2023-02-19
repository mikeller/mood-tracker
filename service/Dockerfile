FROM maven:3.6.3 AS build
WORKDIR /opt/app
COPY MoodTracker/pom.xml /opt/app/
RUN mvn dependency:resolve dependency:resolve-plugins
COPY MoodTracker/src /opt/app/
RUN mvn clean package

FROM tomcat:9
ENV JPDA_ADDRESS=8083
ENV JPDA_TRANSPORT=dt_socket
COPY --from=build /opt/app/target/MoodTracker.war /usr/local/tomcat/webapps/

