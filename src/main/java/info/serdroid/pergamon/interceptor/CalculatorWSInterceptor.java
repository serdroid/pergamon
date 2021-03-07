package info.serdroid.pergamon.interceptor;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.jws.WebMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.serdroid.pergamon.ws.CalculatorWS;

@Priority(Interceptor.Priority.APPLICATION)
@Dependent
@Interceptor
@CalculatorWSCall
public class CalculatorWSInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(CalculatorWSInterceptor.class);

	@AroundInvoke
	public Object saveContextToTLS(InvocationContext invocationContext) throws Exception {
		System.out.println("intercepting " + invocationContext.getMethod().getName());
		logger.debug("{} is intercepting method={} target={}", this.getClass().getName(), invocationContext.getMethod().getName(), invocationContext.getTarget());
		if ( invocationContext.getMethod().getDeclaringClass() == CalculatorWS.class 
				&& invocationContext.getMethod().isAnnotationPresent(WebMethod.class)) {
			((CalculatorWS) invocationContext.getTarget()).setContext();
		}
		return invocationContext.proceed();
	}


}
