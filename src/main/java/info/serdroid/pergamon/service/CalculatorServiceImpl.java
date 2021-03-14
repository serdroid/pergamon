package info.serdroid.pergamon.service;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.common.PergamonConstants;

@ApplicationScoped
public class CalculatorServiceImpl implements CalculatorService {
	private ThreadLocal<Map<String, Object>> currentContext;
	
	public CalculatorServiceImpl() {
		currentContext = new ThreadLocal<>();
	}

	@Override
	public void setCallContext(Map<String, Object> ctx) {
		currentContext.set(ctx);
	}

	@Override
	public int Add(int first, int second) {
		return first + second;
	}

	@Override
	public String getCurrentUserId() {
		String userId = (String) currentContext.get().get(PergamonConstants.USERID_KEY);
		System.out.println("userId from ctx:" + userId);
		return userId;
	}


}
