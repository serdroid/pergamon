package info.serdroid.pergamon.ws;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import org.mockito.Mockito;

import info.serdroid.pergamon.api.CalculatorService;

@ApplicationScoped
public class MockProducer {

	@Produces
    @Singleton
    public CalculatorService calculatorProducer() {
        return Mockito.mock(CalculatorService.class);
    }

}
