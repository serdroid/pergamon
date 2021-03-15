package info.serdroid.pergamon.ws;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
import info.serdroid.pergamon.ws.client.CalculatorWS;
import info.serdroid.pergamon.ws.client.CalculatorWSClient;

@RunWith(Arquillian.class)
public class CalculatorWSTest {

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
        		.addPackages(false, "info.serdroid.pergamon.ws")
        		.addPackages(false, "info.serdroid.pergamon.service")
        		.addPackages(true, "info.serdroid.pergamon.interceptor")
        		.addAsLibraries(archives)
                .addAsWebInfResource("beans-test.xml", "beans.xml")
                ;
        System.out.println(war.toString(true));
        return war;
    }

	@Test
	public void rest_Add() {
		WebClient client = WebClient.create(restUrlPrefix + "calc/add");
		client.type(MediaType.APPLICATION_FORM_URLENCODED);
		client.query("first", 1);
		client.query("second", 2);
		client.accept(MediaType.TEXT_PLAIN);
        Response response = client.get();
		String result = response.readEntity(String.class);
		assertThat(result).isEqualTo("3");
	}

	@Test
	public void soap_Add() throws IOException {
		CalculatorWS calcWS = createWS();
		int result = calcWS.add(1, 2);
		assertThat(result).isEqualTo(3);
	}
	
	@Test
	public void soap_Add_And_Get_Userid() throws IOException {
		CalculatorWS calcWS = createWS();
		String result = calcWS.addAndGetUser(2, 3);
		assertThat(result).isEqualTo("User admin has called, result = 5");
	}
	
	private CalculatorWS createWS() throws IOException {
		CalculatorWS calcWS = CalculatorWSClient.createWS(new URL(testUrl));
		return calcWS;
	}

}
