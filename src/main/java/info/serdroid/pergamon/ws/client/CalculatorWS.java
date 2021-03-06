
package info.serdroid.pergamon.ws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.3
 * Generated source version: 2.0
 * 
 */
@WebService(name = "CalculatorWS", targetNamespace = "http://ws.pergamon.serdroid.info/")
public interface CalculatorWS {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://ws.pergamon.serdroid.info/", className = "info.serdroid.pergamon.ws.client.Add")
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://ws.pergamon.serdroid.info/", className = "info.serdroid.pergamon.ws.client.AddResponse")
    public int add(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "AddAndGetUser")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "AddAndGetUser", targetNamespace = "http://ws.pergamon.serdroid.info/", className = "info.serdroid.pergamon.ws.client.AddAndGetUser")
    @ResponseWrapper(localName = "AddAndGetUserResponse", targetNamespace = "http://ws.pergamon.serdroid.info/", className = "info.serdroid.pergamon.ws.client.AddAndGetUserResponse")
    public String addAndGetUser(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

}
