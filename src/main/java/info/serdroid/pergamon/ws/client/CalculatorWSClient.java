package info.serdroid.pergamon.ws.client;

import java.io.IOException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class CalculatorWSClient {
	private static final String testUrl = "http://localhost:8080/calculator-server/CalculatorWebService?wsdl";
	private static final String targetNS = "http://ws.pergamon.serdroid.info/";
	private static final String serviceName = "CalculatorWebService";

	public static CalculatorWS createWS() throws IOException {
		URL url = new URL(testUrl);
		return createWS(url);
	}

	public static CalculatorWS createWS(URL url) throws IOException {
		QName qname = new QName(targetNS, serviceName);
		Service service = Service.create(url, qname);
		CalculatorWS calc = service.getPort(CalculatorWS.class);
		return calc;
	}

}
