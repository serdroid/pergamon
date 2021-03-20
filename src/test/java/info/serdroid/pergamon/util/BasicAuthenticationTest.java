package info.serdroid.pergamon.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class BasicAuthenticationTest {

	@Test
	public void generateFromEncodedString() {
		String encoded = "Basic YWRtaW46YWRtaW4=";
		BasicAuthentication fromEncoded = new BasicAuthentication(encoded);
		assertThat(fromEncoded.getUserName()).isEqualTo("admin");
		assertThat(fromEncoded.getPassword()).isEqualTo("admin");
	}

	@Test
	public void generateFromUserPass() {
		BasicAuthentication fromUserPass = new BasicAuthentication("admin", "admin");
		assertThat(fromUserPass.getEncoded()).isEqualTo("YWRtaW46YWRtaW4=");
	}

}