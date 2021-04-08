## Pergamon

Pergamon is a sample project that is aimed to demonstrate two topics :

* How to develop a CDI interceptor to intercept SOAP web service calls.
* How to mock a CDI bean in an arquillian managed test suite.


I had to expose both SOAP and REST APIs for a project. So I defined a service interface and injected it into SOAP and REST web service endpoints.

```
public interface CalculatorService {
	public void setCallContext(Map<String, Object> ctxMap);
	public int Add(int first, int second);
	public String AddAndGetUser(int first, int second);
}
```

Here is the CDI injections of CalculatorService in REST endpoint and SOAP web service classes.

```
@Path("calc")
public class CalculatorEndpoint {

	@Inject
	private CalculatorService calculatorService;
...
	

@WebService(name = "CalculatorWS", serviceName = "CalculatorWebService", wsdlLocation = "wsdl/CalculatorWebService.wsdl")
public class CalculatorWS {
	@Inject
	private CalculatorService calculatorService;
...	
```

### Mocking a CDI bean
I want to test my web service setup and I use arquillian for that. But I don't want to test web service's business logic. Instead of that, I want to mock my service implementation. To achieve this I use nothing but the CDI. Just inject the target class into arquillian test class as usual.

```
@RunWith(Arquillian.class)
public class CalculatorWSMockedTest {

	@Inject
	CalculatorService calculatorService;
```	

And the crux of the matter is implementing a CDI producer to provide a mock.

```
@ApplicationScoped
public class MockProducer {

	@Produces
    @Singleton
    public CalculatorService calculatorProducer() {
        return Mockito.mock(CalculatorService.class);
    }
```


### Calling context
We sometimes need information about the caller in our business methods. We can name this information as calling context. I declared a thread-local map in my service class to hold this information.

```
public class CalculatorServiceImpl implements CalculatorService {
	private ThreadLocal<Map<String, Object>> currentContext;
```

I introduced a method in my service interface to set this information into the service. I want to integrate basic authentication into my SOAP and REST web service endpoints. And use the user's information as the calling context.

For the REST endpoint I implemented `javax.ws.rs.container.ContainerRequestFilter` interface. This implementation should be registered as a `javax.ws.rs.ext.Provider`.

```
@Provider
@ApplicationScoped
public class RequestFilter implements ContainerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

	@Inject
	private CalculatorService calculatorService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		...
		MultivaluedMap<String,String> headers = requestContext.getHeaders();
		String authorization = headers.getFirst("Authorization");
		if( null == authorization ) {
			// no Authorization header, return 401 Authorization required
			requestContext.abortWith(Response.status(401, "Authorization Required").build());
			return;
		}
		BasicAuthentication basic = new BasicAuthentication(authorization);
		
		Map<String, Object> context = new HashMap<>();
		context.put(PergamonConstants.USERID_KEY, basic.getUserName());
		calculatorService.setCallContext(context);
	}

```

I implemented a CDI interceptor for the SOAP endpoint. I annotated the CalculatorWS class with this interceptor. Therefore all methods of this class are intercepted by my interceptor implementation. I also define `javax.xml.ws.WebServiceContext` member as a `javax.annotation.Resource` to get http request headers. 

```
@CalculatorWSCall
@WebService(name = "CalculatorWS", serviceName = "CalculatorWebService", wsdlLocation = "wsdl/CalculatorWebService.wsdl")
public class CalculatorWS {
	private WebServiceContext wsContext;
	
	@XmlTransient
	@Resource
	void setWsContext(WebServiceContext wsContext) {
		this.wsContext = wsContext;
	}

``````

To define an interceptor we declare an annotation with `@InterceptorBinding` annotation.

```
@InterceptorBinding
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface CalculatorWSCall {
}
```

And declare the implementation class. This implementation calls setContext method of the CalculatorWS interface.

```
@Interceptor
@CalculatorWSCall
public class CalculatorWSInterceptor {

	@AroundInvoke
	public Object saveContextToTLS(InvocationContext invocationContext) throws Exception {
		if ( invocationContext.getMethod().getDeclaringClass() == CalculatorWS.class 
				&& invocationContext.getMethod().isAnnotationPresent(WebMethod.class)) {
			((CalculatorWS) invocationContext.getTarget()).setContext();
		}
		return invocationContext.proceed();
	}

```

The implementation of the setContext method gets calling user's information from http request headers, creates a context map, and sets it into CalculatorService. So the CalculatorService gets context information.

```
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
```

### Testing thread-local

I want to test if my interceptor working correctly. I have to check if the thread-local variable is set correctly. Tests are running in an arquillian managed container. Therefore test code is running in a different thread. So test code can't access the actual thread-local variable in the CalculatorService.

I introduced a fictional web service method to test the thread-local variable. This method returns a string containing the thread-local variable.

```
	public String AddAndGetUser(int first, int second) {
		int res = Add(first, second);
		// gets current user id from thread local context map
		String userid = getCurrentUserId().orElse("NOT FOUND");
		return String.format("User %s has called, result = %d", userid, res);
	}
```

This method shouldn't be mocked during tests. So I changed my mock producer to return a spied instance of the concrete implementation class.

```
	@Produces
    @Singleton
    public CalculatorService calculatorProducer() {
        return Mockito.spy(new CalculatorServiceImpl());
    }
```

Test code doesn't use mocking.

```
	@Test
	public void soap_Add_And_Get_Userid() throws IOException {
		CalculatorWS calcWS = createWS();
		addAuthorizationHeader(calcWS);
		String result = calcWS.addAndGetUser(2, 3);
		assertThat(result).isEqualTo("User testuser has called, result = 5");
	}
```

