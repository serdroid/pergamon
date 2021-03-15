package info.serdroid.pergamon.api;

import java.util.Map;
import java.util.Optional;

public interface CalculatorService {
	public void setCallContext(Map<String, Object> ctxMap);
	public int Add(int first, int second);
	public String AddAndGetUser(int first, int second);
	public Optional<String> getCurrentUserId();
}
