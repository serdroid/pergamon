package info.serdroid.pergamon.util;

import java.util.Base64;
import java.util.StringTokenizer;

public class BasicAuthentication {
	public static final String AuthorizationScheme = "Basic "; 
	private String userName;
	private String password;
	private String encoded;
	
	public BasicAuthentication(String authorizationHeader) {
		if( ! authorizationHeader.startsWith(AuthorizationScheme)) {
			throw new IllegalArgumentException("This is not a valid Basic Authentication string");
		}
        this.encoded = authorizationHeader.substring(6);
         
        final String usernameAndPassword = new String(Base64.getDecoder().decode(this.encoded));
 
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        this.userName = tokenizer.nextToken();
        this.password = tokenizer.nextToken();
	}
	
	public BasicAuthentication(String user, String passwd) {
        this.userName = user;
        this.password = passwd;
        final String usernameAndPassword = userName + ":" + password;
        this.encoded = Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
	}

	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getEncoded() {
		return encoded;
	}
	public String getAuthorizationHeaderValue() {
		return AuthorizationScheme + encoded;
	}
	
}