# Building
All you need is the `JAVA_HOME` environment variable pointing to a JDK 8 installation and to type `./gradlew` to
build the project.  By default, the build will execute only the steps required for a developer build.  If you set the `onBuildServer` property,
via `-PonBuildServer=true`, then the build will execute additional steps that are normally run on the CI server.

# Deployment

* **from Gradle** -- issuing `./gradlew bootRun` will launch the service using the 'exploded' version of the WAR.
 By default the service is configured to bind itself to a randomly chosen available port so you will have to watch the log messages to see what port that is.
* **from the command line** -- issuing `java -jar build/libs/example-micro-service-0.0.1.war` will launch the service using an embedded Tomcat
  container and is useful in environments where Tomcat is not currently available. As with the Gradle option, the service will select an open port so you
  must examine the log messages to find out what port that is.
* **inside Tomcat** -- deploying the WAR into Tomcat will work just like any other web application giving you full control over the port and context settings

# Configuration
The service can be configured in a variety of ways.  The following order describes the sequence and locations of the configuration options:

1. Command line arguments, eg. --server.port=9000
2. Java System properties,
3. OS environment variables.
4. JNDI attributes from java:comp/env
5. Application properties outside of the packaged jar (application.properties including YAML and profile variants)
6. Application properties packaged inside the jar (application.properties including YAML and profile variants)

The application will search for application.properties in the following locations:

1. A /config sub directory of the current directory
2. The current directory
3. A classpath /config package
4. The classpath root

## RESTful Health Checks

Using simple cURL commands, you should be able to obtain lots of useful information from the service using the following endpoints:

* `/info` to see general information
* `/mappings` to see the available URI mappings
* `/health` to be used by load balancer and monitoring tools in order to determine if the service is running or not
* `/dump` to see a thread dump of the application
* `/metrics` to see various gauges and counters embedded in the application, such a available memory or latency of particular operations
* `/shutdown` to shutdown the application via a POST (if enabled)
* `/autoconfig` to see how Spring Boot applied configuration logic to the application
* `/beans` to see Spring Beans in the application
* `/trace` to see trace information
* `/env` to see the environment the application is operating under
* `/configprops` configuration properties broken out by configuration

Issuing `curl http://localhost:42402/info` should get you something similar to

```
{
  "app" : {
    "description" : "An example micro-service",
    "version" : "0.0.1",
    "name" : "Example Micro-Service"
  }
}
```
While ` curl --data "" http://localhost:42402/shutdown` should shutdown the application and return something similar to

```
{
  "message" : "Shutting down, bye..."
}
