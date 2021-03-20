package info.serdroid.pergamon.ws;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.jaxrs.client.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.impl.gradle.Gradle;
import org.jboss.shrinkwrap.resolver.impl.gradle.ScopeType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.util.BasicAuthentication;
import info.serdroid.pergamon.ws.client.CalculatorWS;
import info.serdroid.pergamon.ws.client.CalculatorWSClient;

@RunWith(Arquillian.class)
public class CalculatorWSMockedTest {

	private static final String testUrl = "http://localhost:19080/calculator-server/CalculatorWebService?wsdl";
	private static final String restUrlPrefix = "http://localhost:19080/calculator-server/rest/";

	@Inject
	CalculatorService calculatorService;
	
    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "calculator-server.war");
		Archive<?>[] archives = Gradle.resolver().forProjectDirectory(".")
				.importDependencies(ScopeType.COMPILE)
				.resolve()
				.asList(JavaArchive.class).toArray(new Archive[0]);
        war.addPackages(false, "info.serdroid.pergamon.api")
        		.addPackages(false, "info.serdroid.pergamon.rest")
        		.addPackages(false, "info.serdroid.pergamon.filter")
        		.addPackages(true, "info.serdroid.pergamon.ws")
        		.addPackages(true, "info.serdroid.pergamon.interceptor")
        		.addAsLibraries(archives)
                .addAsWebInfResource("beans-test.xml", "beans.xml")
                ;
        System.out.println(war.toString(true));
        return war;
    }

	@Test
	public void rest_Add() {
		Mockito.when(calculatorService.Add(1, 2)).thenReturn(5);
		WebClient client = WebClient.create(restUrlPrefix + "calc/add");
		client.type(MediaType.APPLICATION_FORM_URLENCODED);
		client.query("first", 1);
		client.query("second", 2);
		client.accept(MediaType.TEXT_PLAIN);
		BasicAuthentication basicAuthentication = new BasicAuthentication("testuser", "passwd");
		String basicAuth =  "Basic " + basicAuthentication.getEncoded();
		client.header("Authorization", basicAuth);
		Response response = client.get();
		String result = response.readEntity(String.class);
		assertThat(result).isEqualTo("5");
	}

	@Test
	public void soap_Add() throws IOException {
		Mockito.when(calculatorService.Add(1, 2)).thenReturn(5);
		CalculatorWS calcWS = createWS();
		addAuthorizationHeader(calcWS);
		int result = calcWS.add(1, 2);
		assertThat(result).isEqualTo(5);
	}

	private void addAuthorizationHeader(CalculatorWS calcWS) {
		Map<String, Object> requestContext = ((BindingProvider) calcWS).getRequestContext();
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		BasicAuthentication basicAuthentication = new BasicAuthentication("testuser", "passwd");
		String basicAuth =  "Basic " + basicAuthentication.getEncoded();
        headers.put("Authorization", Collections.singletonList(basicAuth));
		requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	}
	
	@Test
	public void soap_Add_And_Get_Userid() throws IOException {
		CalculatorWS calcWS = createWS();
		addAuthorizationHeader(calcWS);
		String result = calcWS.addAndGetUser(2, 3);
		assertThat(result).isEqualTo("User testuser has called, result = 5");
	}
	
	private CalculatorWS createWS() throws IOException {
		return CalculatorWSClient.createWS(new URL(testUrl));
	}

}
