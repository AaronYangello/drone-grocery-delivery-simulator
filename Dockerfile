FROM openjdk:17

ARG SRC_DIR

RUN mkdir -p /app
WORKDIR /app

COPY mvnw mvnw
COPY .mvn .mvn
COPY ${SRC_DIR}/pom.xml pom.xml

COPY test_scenarios ./
COPY test_results ./

RUN sh mvnw dependency:go-offline

COPY ${SRC_DIR}/ ./

RUN sh mvnw package -o
RUN rm -f target/*SNAPSHOT*

ARG ARTIFACT_SUFFIX
RUN cp target/cs6310-${SRC_DIR}-*${ARTIFACT_SUFFIX}.jar app.jar

ADD http://mirror.centos.org/centos/8/BaseOS/x86_64/os/Packages/diffutils-3.6-6.el8.x86_64.rpm /tmp/diffutils.rpm
RUN rpm -i /tmp/diffutils.rpm

ENTRYPOINT java -jar app.jar
