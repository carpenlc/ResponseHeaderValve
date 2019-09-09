# ResponseHeaderValve
As part of some required system hardening activities we were asked to enable HTTP Strict Transport Security (HSTS).  This entails adding a "Strict-Transport-Security" response header.  The web subsystem of the JBoss 6.x application container does not provide a mechanism for adding custom response headers.  The code in this repository implements a global valve that inserts the HSTS response header for all client requests.
Code has been updated to include a header to prevent MIME attacks, and an IE8 header to help protect against XSS attacks.

## Download and Build the Source
* Minimum requirements:
    * Java Development Kit (v1.8.0 or higher)
    * GIT (v1.7 or higher)
    * Maven (v3.3 or higher)
    * JBoss (v6.x)
* Download source
```
# cd /var/local
# git clone https://github.com/carpenlc/ResponseHeaderValve.git
```
* Build the source
```
# cd ResponseHeaderValve
# mvn clean package
```
## Installation
The valve will be installed as a JBoss module.
```
# mkdir -p ${JBOSS_HOME}/modules/system/layers/base/mil/nga/npd/ResponseHeaderValve/main
# cp ./target/ResponseHeaderValve-1.0.0.jar ${JBOSS_HOME}/modules/system/layers/base/mil/nga/npd/ResponseHeaderValve/main
# cp ./src/main/resources/module.xml ${JBOSS_HOME}/modules/system/layers/base/mil/nga/npd/ResponseHeaderValve/main
```
Add the valve to the JBoss web subsystem.  My JBoss CLI foo is not strong (expecially due to running in domain mode).  As such we manually added the valve to the domain.xml file:
```
<valve name="HSTS-headers" 
       module="mil.nga.npd.ResponseHeaderValve" 
       class-name="mil.nga.npd.ResponseHeaderValve.InsertResponseHeaders" />
```
Restart JBoss.
