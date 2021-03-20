package info.serdroid.pergamon.ws;

import java.util.HashMap;
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
import info.serdroid.pergamon.util.BasicAuthentication;

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
	public void setContext() {
		HttpServletRequest req = (HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
		String authHeader = req.getHeader("Authorization");
		if ( authHeader == null ) {
			throw new RuntimeException("Authorization Required");
		}
		BasicAuthentication basic = new BasicAuthentication(authHeader);
		Map<String, Object> context = new HashMap<>();
		context.put(PergamonConstants.USERID_KEY, basic.getUserName());
		calculatorService.setCallContext(context);

	}

}
