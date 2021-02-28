package info.serdroid.pergamon.ws;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.service.CalculatorServiceImpl;

public class CalculatorServiceTest {

	@Test
	public void testAdd() {
		CalculatorService calcService = new CalculatorServiceImpl();
		int sum = calcService.Add(1, 2);
		assertThat(sum).isEqualTo(3);
	}

}
