package info.serdroid.pergamon.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import info.serdroid.pergamon.api.CalculatorService;

@ApplicationScoped
@Path("calc")
public class CalculatorEndpoint {

	@Inject
	CalculatorService calculatorService;
	
	@GET
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response add(@QueryParam("first") int first, @QueryParam("second") int second ) {
		int result = calculatorService.Add(first, second);
		return Response.ok(result).build();
	}

}
