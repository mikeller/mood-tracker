FROM maven:3.9.9 AS build
WORKDIR /opt/app
COPY MoodTracker/pom.xml /opt/app/
RUN mvn dependency:resolve dependency:resolve-plugins
COPY MoodTracker/src /opt/app/src
RUN mvn clean package

FROM tomcat:9
COPY --from=build /opt/app/target/ROOT.war /usr/local/tomcat/webapps/
