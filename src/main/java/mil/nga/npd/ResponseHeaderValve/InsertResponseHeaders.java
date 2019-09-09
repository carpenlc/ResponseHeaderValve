package mil.nga.npd.ResponseHeaderValve;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

/**
 * JBoss 6.X does not provide a mechanism for adding custom response headers 
 * through the web subsystem.  This class is an implementation of a simple 
 * global valve to insert response headers in the processing pipeline.  This 
 * implementation inserts the HTTP Strict-Transport-Security (HSTS) header 
 * as required by DoD STIGs.  Updated to add headers to prevent MIME attacks
 * and the IE8 XSS Protection headers.
 * 
 * @author L. Craig Carpenter
 */
public class InsertResponseHeaders extends ValveBase {
	
	public static final String X_CONTENT_TYPE_HEADER_NAME = "X-Content-Type-Options";
	public static final String X_CONTENT_TYPE_HEADER_VALUE = "nosniff";
	
	public static final String XSS_PROTECTION_HEADER_NAME = "X-XSS-Protection";
	public static final String XSS_PROTECTION_HEADER_VALUE = "1; mode=block";
	
	public static final String HSTS_RESPONSE_HEADER_NAME = "Strict-Transport-Security";
	public static final String HSTS_RESPONSE_HEADER_VALUE = "max-age=31536000; includeSubDomains; preload";

	@Override
	public void invoke(Request req, Response resp) throws IOException, ServletException {
		
		// Add the HSTS header
		resp.addHeader(HSTS_RESPONSE_HEADER_NAME, HSTS_RESPONSE_HEADER_VALUE);

		// Add the X-Content-Type-Options header
		resp.addHeader(X_CONTENT_TYPE_HEADER_NAME, X_CONTENT_TYPE_HEADER_VALUE);
		
		// Add the IE8 XSS protection header
		resp.addHeader(XSS_PROTECTION_HEADER_NAME, XSS_PROTECTION_HEADER_VALUE);
		
		// Continue with the rest of the pipeline
		getNext().invoke(req, resp);
	}
}
