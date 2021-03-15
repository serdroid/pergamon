package info.serdroid.pergamon.ws.mock;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import org.mockito.Mockito;

import info.serdroid.pergamon.api.CalculatorService;
import info.serdroid.pergamon.service.CalculatorServiceImpl;

@ApplicationScoped
public class MockProducer {

	@Produces
    @Singleton
    public CalculatorService calculatorProducer() {
        return Mockito.spy(new CalculatorServiceImpl());
    }

}
