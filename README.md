# ResponseHeaderValve
As part of some required system hardening activities we were asked to enable HTTP Strict Transport Security (HSTS).  This entails adding a "Strict-Transport-Security" response header.  The web subsystem of the JBoss 6.x application container does not provide a mechanism for adding custom response headers.  The code in this repository implements a global valve that inserts the HSTS response header for all client requests.

