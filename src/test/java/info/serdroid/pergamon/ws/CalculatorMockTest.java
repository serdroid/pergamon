package info.serdroid.pergamon.ws;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.rest.CalculatorEndpoint;

public class CalculatorMockTest {
	@Mock
	CalculatorService calculatorService;

	@InjectMocks
	CalculatorEndpoint calculatorEndpoint;
	
	@Rule
    public MockitoRule initRule = MockitoJUnit.rule();
	
	@Test
	public void testMockedAdd() {
		Mockito.when(calculatorService.Add(1, 2)).thenReturn(9);
		Response response = calculatorEndpoint.add(1, 2);
		int sum = response.readEntity(Integer.class);
		assertThat(sum).isEqualTo(9);
	}

}
