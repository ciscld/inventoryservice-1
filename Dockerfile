# IBM Java SDK UBI is not available on public docker yet. Use regular
# base as builder until this is ready. For reference:
# https://github.com/ibmruntimes/ci.docker/tree/master/ibmjava/8/sdk/ubi-min

FROM ibmjava:8-sdk AS builder
LABEL maintainer="IBM Java Engineering at IBM Cloud"

#VOLUME /tmp
WORKDIR /app
RUN apt-get update && apt-get install -y maven

COPY pom.xml .
RUN mvn -N io.takari:maven:wrapper -Dmaven=3.5.0

COPY . /app
RUN chmod +x mvnw
RUN ./mvnw install

ARG bx_dev_user=root
ARG bx_dev_userid=1000
RUN BX_DEV_USER=$bx_dev_user
RUN BX_DEV_USERID=$bx_dev_userid
RUN if [ $bx_dev_user != "root" ]; then useradd -ms /bin/bash -u $bx_dev_userid $bx_dev_user; fi

FROM adoptopenjdk/openjdk8:ubi-jre
#ADD orderdb.h2.db  /tmp/orderdb.h2.db

# Copy over app from builder image into the runtime image.
RUN mkdir /opt/app
COPY --from=builder /app/target/inventory-0.0.1-SNAPSHOT.jar /opt/app/app.jar

# Create mongo directory
RUN mkdir /opt/app/.embedmongo

ENTRYPOINT [ "sh", "-c", "java -jar /opt/app/app.jar" ]
