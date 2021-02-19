package info.serdroid.pergamon.service;

import static org.assertj.core.api.Assertions.assertThat;

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

@RunWith(Arquillian.class)
public class CalculatorWSTest {

	private static final String restUrlPrefix = "http://localhost:19080/calculator-server/rest/";

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "calculator-server.war");
		Archive<?>[] archives = Gradle.resolver().forProjectDirectory(".")
				.importDependencies(ScopeType.COMPILE)
				.resolve()
				.asList(JavaArchive.class).toArray(new Archive[0]);
        war.addPackages(false, "info.serdroid.pergamon.api")
        		.addPackages(false, "info.serdroid.pergamon.service")
        		.addPackages(false, "info.serdroid.pergamon.rest")
        		.addAsLibraries(archives)
                .addAsWebInfResource("beans-test.xml", "beans.xml")
                ;
        System.out.println(war.toString(true));
        return war;
    }

	@Test
	public void testRestAdd() {
		WebClient client = WebClient.create(restUrlPrefix + "calc");
		client.type(MediaType.APPLICATION_FORM_URLENCODED);
		client.query("first", 1);
		client.query("second", 2);
		client.accept(MediaType.TEXT_PLAIN);
        Response response = client.get();
		String result = response.readEntity(String.class);
		assertThat(result).isEqualTo("3");
	}

}
