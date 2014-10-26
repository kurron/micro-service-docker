#!/bin/bash

#clean up from previous runs
#sudo rm -rf /tmp/mongodb-data /tmp/rabbitmq

# launch MongoDB, mapping exposed ports and use /tmp/mongodb-data for persistent storage
docker run --detach --net="bridge" --publish 27017:27017 --hostname="mongodb" --name="mongodb" --memory="256m" --volume /tmp/mongodb-data:/data mongo:latest

# launch RabbitMQ, mapping exposed ports and use /tmp/rabbitmq for persistent storage
docker run --detach  --net="bridge" --publish 5672:5672 --publish 15672:15672 --hostname="rabbitmq" --name="rabbitmq" --memory="256m" --volume /tmp/rabbitmq/log:/data/log --volume /tmp/rabbitmq/mnesia:/data/mnesia kurron/rabbitmq-docker-container:latest

# launch the application, map its port to localhost:8080 and have Docker pass the environment to the application
docker run --rm --detach --hostname="micro-service" --name="micro-service" --memory="256m" --publish 8080:8080 kurron/micro-service-docker:latest /bin/bash --spring.data.mongodb.host="10.0.2.15" --spring.rabbitmq.host="10.0.2.15"  --info.app.description="command-line" /bin/bash

docker run --rm --interactive --tty --hostname="micro-service" --name="micro-service" --memory="256m" --publish 8080:8080 kurron/micro-service-docker:latest /bin/bash --spring.data.mongodb.host="10.0.2.15" --spring.rabbitmq.host="10.0.2.15"  --info.app.description="command-line" /bin/bash
