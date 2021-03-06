
package info.serdroid.pergamon.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the info.serdroid.pergamon.ws.client package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddAndGetUser_QNAME = new QName("http://ws.pergamon.serdroid.info/", "AddAndGetUser");
    private final static QName _AddAndGetUserResponse_QNAME = new QName("http://ws.pergamon.serdroid.info/", "AddAndGetUserResponse");
    private final static QName _Add_QNAME = new QName("http://ws.pergamon.serdroid.info/", "add");
    private final static QName _AddResponse_QNAME = new QName("http://ws.pergamon.serdroid.info/", "addResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: info.serdroid.pergamon.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddAndGetUser }
     * 
     */
    public AddAndGetUser createAddAndGetUser() {
        return new AddAndGetUser();
    }

    /**
     * Create an instance of {@link AddAndGetUserResponse }
     * 
     */
    public AddAndGetUserResponse createAddAndGetUserResponse() {
        return new AddAndGetUserResponse();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAndGetUser }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddAndGetUser }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.pergamon.serdroid.info/", name = "AddAndGetUser")
    public JAXBElement<AddAndGetUser> createAddAndGetUser(AddAndGetUser value) {
        return new JAXBElement<AddAndGetUser>(_AddAndGetUser_QNAME, AddAndGetUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAndGetUserResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddAndGetUserResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.pergamon.serdroid.info/", name = "AddAndGetUserResponse")
    public JAXBElement<AddAndGetUserResponse> createAddAndGetUserResponse(AddAndGetUserResponse value) {
        return new JAXBElement<AddAndGetUserResponse>(_AddAndGetUserResponse_QNAME, AddAndGetUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Add }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.pergamon.serdroid.info/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.pergamon.serdroid.info/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

}
