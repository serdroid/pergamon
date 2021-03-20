package info.serdroid.pergamon.service;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.common.PergamonConstants;
import info.serdroid.pergamon.filter.RequestFilter;

@ApplicationScoped
public class CalculatorServiceImpl implements CalculatorService {
	private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
	private ThreadLocal<Map<String, Object>> currentContext;
	
	public CalculatorServiceImpl() {
		currentContext = new ThreadLocal<>();
	}

	@Override
	public void setCallContext(Map<String, Object> ctx) {
		logger.debug("setting current context");
		currentContext.set(ctx);
	}

	@Override
	public int Add(int first, int second) {
		return first + second;
	}

	Optional<String> getCurrentUserId() {
		Map<String, Object> ctx = currentContext.get();
		if ( ctx == null ) {
			return Optional.empty();
		}
		String userId = (String) ctx.get(PergamonConstants.USERID_KEY);
		return Optional.ofNullable(userId);
	}

	@Override
	public String AddAndGetUser(int first, int second) {
		int res = Add(first, second);
		// gets current user id from thread local context map
		String userid = getCurrentUserId().orElse("NOT FOUND");
		return String.format("User %s has called, result = %d", userid, res);
	}

}
