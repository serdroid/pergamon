package info.serdroid.pergamon.ws;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.interceptor.CalculatorWSCall;

@ApplicationScoped
@CalculatorWSCall
@WebService(name = "CalculatorWS", serviceName = "CalculatorWebService", wsdlLocation = "wsdl/CalculatorWebService.wsdl")
public class CalculatorWS {
	@Inject
	CalculatorService calculatorService;
	
	@WebMethod
	public int add(int first, int second) {
		return calculatorService.Add(first, second);
	}

	@WebMethod(exclude = true)
	public void setContext() {
		System.out.println("setting context");
	}

}
