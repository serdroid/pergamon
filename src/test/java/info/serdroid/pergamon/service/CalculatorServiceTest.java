package info.serdroid.pergamon.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class CalculatorServiceTest {

	@Test
	public void testAdd() {
		CalculatorService calcService = new CalculatorServiceImpl();
		int sum = calcService.Add(1, 2);
		assertThat(sum).isEqualTo(3);
	}

}
