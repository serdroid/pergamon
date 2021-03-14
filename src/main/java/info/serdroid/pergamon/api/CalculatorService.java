package info.serdroid.pergamon.api;

import java.util.Map;

public interface CalculatorService {
	public void setCallContext(Map<String, Object> ctxMap);
	public int Add(int first, int second);
	public String getCurrentUserId();
}
