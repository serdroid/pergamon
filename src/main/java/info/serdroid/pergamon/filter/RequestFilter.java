package info.serdroid.pergamon.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.common.PergamonConstants;
import info.serdroid.pergamon.util.BasicAuthentication;

@Provider
@ApplicationScoped
public class RequestFilter implements ContainerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

	@Inject
	private CalculatorService calculatorService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String path = requestContext.getUriInfo().getPath();
		logger.info("path={}", path);
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		
		MultivaluedMap<String,String> headers = requestContext.getHeaders();
		String authorization = headers.getFirst("Authorization");
		logger.info("Processing Authorization header: {} for path={}", authorization, path );
		if( null == authorization ) {
			// no Authorization header, return 401 Authorization required
			logger.debug("no Authorization header, returning 401 for path=", path );
			requestContext.abortWith(Response.status(401, "Authorization Required").build());
			return;
		}
		BasicAuthentication basic = new BasicAuthentication(authorization);
		
		Map<String, Object> context = new HashMap<>();
		context.put(PergamonConstants.USERID_KEY, basic.getUserName());
		calculatorService.setCallContext(context);
	}
}