package info.serdroid.pergamon.ws;

import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.common.PergamonConstants;
import info.serdroid.pergamon.interceptor.CalculatorWSCall;

@ApplicationScoped
@CalculatorWSCall
@WebService(name = "CalculatorWS", serviceName = "CalculatorWebService", wsdlLocation = "wsdl/CalculatorWebService.wsdl")
public class CalculatorWS {
	private WebServiceContext wsContext;
	@Inject
	private CalculatorService calculatorService;
	
	
	@XmlTransient
	@Resource
	void setWsContext(WebServiceContext wsContext) {
		this.wsContext = wsContext;
	}
	
	@WebMethod
	public int add(int first, int second) {
		return calculatorService.Add(first, second);
	}

	@WebMethod
	public String AddAndGetUser(int first, int second) {
		return calculatorService.AddAndGetUser(first, second);
	}
	
	@WebMethod(exclude = true)
	public void setContext(Map<String, Object> context) {
		HttpServletRequest req = (HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
		String authHeader = req.getHeader("Authorization");
		context.put(PergamonConstants.USERID_KEY, "admin");
		calculatorService.setCallContext(context);
	}

}
