ARG VERSION="1.0.0-SNAPSHOT"

# Using maven base image in builder stage to build Java code.
FROM maven:3-openjdk-11-slim as builder

WORKDIR /usr/share/app
COPY pom.xml .
# Downloads all packages defined in pom.xml
RUN mvn clean package
COPY src src
# Build the source code to generate the fatjar
RUN mvn clean package -Dmaven.test.skip=true

# Java Runtime as the base for final image
FROM openjdk:11-jre-slim-buster

ARG VERSION
ENV JAR="iudx.auditing.server-cluster-${VERSION}-fat.jar"

WORKDIR /usr/share/app

# Copying clustered fatjar from builder stage to final image
COPY --from=builder /usr/share/app/target/${JAR} ./fatjar.jar

EXPOSE 9000
# Creating a non-root user
RUN  useradd -r -u 1001 -g root audit-user
# Setting non-root user to use when container starts
USER audit-user