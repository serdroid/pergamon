package info.serdroid.pergamon.ws;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.service.CalculatorServiceImpl;

public class CalculatorServiceTest {

	@Test
	public void testAdd() {
		CalculatorService calcService = new CalculatorServiceImpl();
		Map<String, Object> ctx = new HashMap<>();
		ctx.put("userId", "admin");
		calcService.setCallContext(ctx);
		int sum = calcService.Add(1, 2);
		assertThat(sum).isEqualTo(3);
	}

}
