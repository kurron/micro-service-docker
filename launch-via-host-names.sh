#!/bin/bash

# launch MongoDB, don't map any ports and use /tmp/mongodb-data for persistent storage
docker run --detach --hostname="mongodb" --name="mongodb" --memory="256m" --volume /tmp/mongodb-data:/data mongo:latest

# launch RabbitMQ, don't map any ports and use /tmp/rabbitmq for persistent storage
docker run --detach --hostname="rabbitmq" --name="rabbitmq" --memory="256m" --volume /tmp/rabbitmq/log:/data/log --volume /tmp/rabbitmq/mnesia:/data/mnesia kurron/rabbitmq-docker-container:latest

# launch the application, map its port to localhost:8080 and have Docker take care of mapping the MongoDB and RabbitMQ ports to this container
docker run --detach --hostname="micro-service" --name="micro-service" --memory="256m" --publish 8080:8080 --link mongodb:mongodb --link rabbitmq:rabbitmq  kurron/micro-service-docker:latest
#docker run --interactive --tty --hostname="micro-service" --name="micro-service" --memory="256m" --publish 8080:8080 --link mongodb:mongodb --link rabbitmq:rabbitmq  kurron/micro-service-docker:latest /bin/bash
