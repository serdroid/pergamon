package info.serdroid.pergamon.service;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import info.serdroid.pergamon.api.CalculatorService;

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
		String userId = (String) currentContext.get().get("userId");
		System.out.println("userId from ctx:" + userId);
		
		return first + second;
	}


}
