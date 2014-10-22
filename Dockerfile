# Java Environment
FROM kurron/java-8-docker-container:latest

MAINTAINER Ron Kurr <kurr@kurron.org>

# create a configuration directory that we can use a a mount point
VOLUME ["/root/config"]

# copy the application
COPY service/build/libs/service-0.0.0-SNAPSHOT.war /root/

# expose port 8080 to the outside world
EXPOSE 8080

# set the working directory
WORKDIR /root

# start the application
ENTRYPOINT java -jar /root/service-0.0.0-SNAPSHOT.war