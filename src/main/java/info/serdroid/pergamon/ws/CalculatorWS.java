package info.serdroid.pergamon.ws;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import info.serdroid.pergamon.api.CalculatorService;

@ApplicationScoped
@WebService(name = "CalculatorWS", serviceName = "CalculatorWebService")
public class CalculatorWS {
	@Inject
	CalculatorService calculatorService;
	
	@WebMethod
	public int add(int first, int second) {
		return calculatorService.Add(first, second);
	}

}