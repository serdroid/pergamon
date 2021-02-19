package info.serdroid.pergamon.service;

import javax.enterprise.context.ApplicationScoped;

import info.serdroid.pergamon.api.CalculatorService;

@ApplicationScoped
public class CalculatorServiceImpl implements CalculatorService {

	@Override
	public int Add(int first, int second) {
		return first + second;
	}

}
