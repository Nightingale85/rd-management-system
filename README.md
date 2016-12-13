### Resource development management system

### Required software:
- JDK 1.8 or above
- Apache Maven 3.3.9 or above

### Quick start for overview
## REST server
 mvn -f back-end/web/pom.xml jetty:run
## Client server
 mvn -f front-end/pom.xml jetty:run
 
### Quick start for developing
 - Build the root project: mvn clean install
 - Install Intellij Idea Jetty runner plugin
 - Edit configurations > Add new Configuration > Jetty runner
 - For REST server:
   - Specify absolute path to beck-end/web/src/main/webapp folder
   - Specify absolute path to beck-end/web/target/classes folder
 - For Client server:
   - Specify absolute path to front-end/src/main/webapp folder
   - Specify absolute path to front-end/target/classes folder
 - Ok > Press Play, enjoy
 
### Build and deploy for production
- you should achieve .war file to deploy it to container
  (run 'mvn clean install' in directory **\rd-management-system)
## REST server
   - .war will be in directory **\rd-management-system\beck-end\web\target
## Client server
   - .war will be in directory **\rd-management-system\front-end\target\
- to deploy move .war file to directory webapps of Tomcat
- for automation data population in REST server database use VM option: "-Dspring.profiles.active=tmp-data"

### JsonDoc for REST API
- start REST server
- use request /rest
- push "Get documentation"
