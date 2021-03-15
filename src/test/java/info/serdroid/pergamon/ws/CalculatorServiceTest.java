package info.serdroid.pergamon.ws;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.common.PergamonConstants;
import info.serdroid.pergamon.service.CalculatorServiceImpl;

public class CalculatorServiceTest {

	@Test
	public void add() {
		CalculatorService calcService = new CalculatorServiceImpl();
		int sum = calcService.Add(1, 2);
		assertThat(sum).isEqualTo(3);
	}

	@Test
	public void getCurrentUserId() {
		CalculatorService calcService = new CalculatorServiceImpl();
		Map<String, Object> ctx = new HashMap<>();
		String userId = "admin";
		ctx.put(PergamonConstants.USERID_KEY, userId);
		calcService.setCallContext(ctx);
		String currentUserId = calcService.getCurrentUserId().orElse("");
		assertThat(currentUserId).isEqualTo(userId);
	}

}
