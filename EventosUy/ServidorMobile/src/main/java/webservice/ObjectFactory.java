
package webservice;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservice package. 
 * <p>An ObjectFactory allows you to programmatically 
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

    private static final QName _Exception_QNAME = new QName("http://webservice.java.main/", "Exception");
    private static final QName _ExisteCategoriaFault_QNAME = new QName("http://webservice.java.main/", "ExisteCategoriaFault");
    private static final QName _ExisteEdicion_QNAME = new QName("http://webservice.java.main/", "ExisteEdicion");
    private static final QName _ExisteEventoExcepcion_QNAME = new QName("http://webservice.java.main/", "ExisteEventoExcepcion");
    private static final QName _NoTieneCupo_QNAME = new QName("http://webservice.java.main/", "NoTieneCupo");
    private static final QName _YaTieneCompra_QNAME = new QName("http://webservice.java.main/", "YaTieneCompra");
    private static final QName _DtAsistente_QNAME = new QName("http://webservice.java.main/", "dtAsistente");
    private static final QName _DtCompra_QNAME = new QName("http://webservice.java.main/", "dtCompra");
    private static final QName _DtCompraWeb_QNAME = new QName("http://webservice.java.main/", "dtCompraWeb");
    private static final QName _DtEdicion_QNAME = new QName("http://webservice.java.main/", "dtEdicion");
    private static final QName _DtEvento_QNAME = new QName("http://webservice.java.main/", "dtEvento");
    private static final QName _DtOrganizador_QNAME = new QName("http://webservice.java.main/", "dtOrganizador");
    private static final QName _DtPatrocinio_QNAME = new QName("http://webservice.java.main/", "dtPatrocinio");
    private static final QName _DtTipoRegistro_QNAME = new QName("http://webservice.java.main/", "dtTipoRegistro");
    private static final QName _DtUsuario_QNAME = new QName("http://webservice.java.main/", "dtUsuario");
    private static final QName _ExisteTipoRegistro_QNAME = new QName("http://webservice.java.main/", "existeTipoRegistro");
    private static final QName _StringArray_QNAME = new QName("http://webservice.java.main/", "stringArray");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     * @return
     *     the new instance of {@link Exception }
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link ExisteCategoriaExcepcion }
     * 
     * @return
     *     the new instance of {@link ExisteCategoriaExcepcion }
     */
    public ExisteCategoriaExcepcion createExisteCategoriaExcepcion() {
        return new ExisteCategoriaExcepcion();
    }

    /**
     * Create an instance of {@link ExisteEdicion }
     * 
     * @return
     *     the new instance of {@link ExisteEdicion }
     */
    public ExisteEdicion createExisteEdicion() {
        return new ExisteEdicion();
    }

    /**
     * Create an instance of {@link ExisteEventoExcepcion }
     * 
     * @return
     *     the new instance of {@link ExisteEventoExcepcion }
     */
    public ExisteEventoExcepcion createExisteEventoExcepcion() {
        return new ExisteEventoExcepcion();
    }

    /**
     * Create an instance of {@link NoTieneCupo }
     * 
     * @return
     *     the new instance of {@link NoTieneCupo }
     */
    public NoTieneCupo createNoTieneCupo() {
        return new NoTieneCupo();
    }

    /**
     * Create an instance of {@link YaTieneCompra }
     * 
     * @return
     *     the new instance of {@link YaTieneCompra }
     */
    public YaTieneCompra createYaTieneCompra() {
        return new YaTieneCompra();
    }

    /**
     * Create an instance of {@link DtAsistente }
     * 
     * @return
     *     the new instance of {@link DtAsistente }
     */
    public DtAsistente createDtAsistente() {
        return new DtAsistente();
    }

    /**
     * Create an instance of {@link DtCompra }
     * 
     * @return
     *     the new instance of {@link DtCompra }
     */
    public DtCompra createDtCompra() {
        return new DtCompra();
    }

    /**
     * Create an instance of {@link DtCompraWeb }
     * 
     * @return
     *     the new instance of {@link DtCompraWeb }
     */
    public DtCompraWeb createDtCompraWeb() {
        return new DtCompraWeb();
    }

    /**
     * Create an instance of {@link DtEdicion }
     * 
     * @return
     *     the new instance of {@link DtEdicion }
     */
    public DtEdicion createDtEdicion() {
        return new DtEdicion();
    }

    /**
     * Create an instance of {@link DtEvento }
     * 
     * @return
     *     the new instance of {@link DtEvento }
     */
    public DtEvento createDtEvento() {
        return new DtEvento();
    }

    /**
     * Create an instance of {@link DtOrganizador }
     * 
     * @return
     *     the new instance of {@link DtOrganizador }
     */
    public DtOrganizador createDtOrganizador() {
        return new DtOrganizador();
    }

    /**
     * Create an instance of {@link DtPatrocinio }
     * 
     * @return
     *     the new instance of {@link DtPatrocinio }
     */
    public DtPatrocinio createDtPatrocinio() {
        return new DtPatrocinio();
    }

    /**
     * Create an instance of {@link DtTipoRegistro }
     * 
     * @return
     *     the new instance of {@link DtTipoRegistro }
     */
    public DtTipoRegistro createDtTipoRegistro() {
        return new DtTipoRegistro();
    }

    /**
     * Create an instance of {@link DtUsuario }
     * 
     * @return
     *     the new instance of {@link DtUsuario }
     */
    public DtUsuario createDtUsuario() {
        return new DtUsuario();
    }

    /**
     * Create an instance of {@link ExisteTipoRegistro }
     * 
     * @return
     *     the new instance of {@link ExisteTipoRegistro }
     */
    public ExisteTipoRegistro createExisteTipoRegistro() {
        return new ExisteTipoRegistro();
    }

    /**
     * Create an instance of {@link StringArrayWrapper }
     * 
     * @return
     *     the new instance of {@link StringArrayWrapper }
     */
    public StringArrayWrapper createStringArrayWrapper() {
        return new StringArrayWrapper();
    }

    /**
     * Create an instance of {@link Ciudad }
     * 
     * @return
     *     the new instance of {@link Ciudad }
     */
    public Ciudad createCiudad() {
        return new Ciudad();
    }

    /**
     * Create an instance of {@link DtEdicionArray }
     * 
     * @return
     *     the new instance of {@link DtEdicionArray }
     */
    public DtEdicionArray createDtEdicionArray() {
        return new DtEdicionArray();
    }

    /**
     * Create an instance of {@link DtTipoRegistroArray }
     * 
     * @return
     *     the new instance of {@link DtTipoRegistroArray }
     */
    public DtTipoRegistroArray createDtTipoRegistroArray() {
        return new DtTipoRegistroArray();
    }

    /**
     * Create an instance of {@link CiudadArray }
     * 
     * @return
     *     the new instance of {@link CiudadArray }
     */
    public CiudadArray createCiudadArray() {
        return new CiudadArray();
    }

    /**
     * Create an instance of {@link DtEventoArray }
     * 
     * @return
     *     the new instance of {@link DtEventoArray }
     */
    public DtEventoArray createDtEventoArray() {
        return new DtEventoArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExisteCategoriaExcepcion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExisteCategoriaExcepcion }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "ExisteCategoriaFault")
    public JAXBElement<ExisteCategoriaExcepcion> createExisteCategoriaFault(ExisteCategoriaExcepcion value) {
        return new JAXBElement<>(_ExisteCategoriaFault_QNAME, ExisteCategoriaExcepcion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExisteEdicion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExisteEdicion }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "ExisteEdicion")
    public JAXBElement<ExisteEdicion> createExisteEdicion(ExisteEdicion value) {
        return new JAXBElement<>(_ExisteEdicion_QNAME, ExisteEdicion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExisteEventoExcepcion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExisteEventoExcepcion }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "ExisteEventoExcepcion")
    public JAXBElement<ExisteEventoExcepcion> createExisteEventoExcepcion(ExisteEventoExcepcion value) {
        return new JAXBElement<>(_ExisteEventoExcepcion_QNAME, ExisteEventoExcepcion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoTieneCupo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoTieneCupo }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "NoTieneCupo")
    public JAXBElement<NoTieneCupo> createNoTieneCupo(NoTieneCupo value) {
        return new JAXBElement<>(_NoTieneCupo_QNAME, NoTieneCupo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link YaTieneCompra }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link YaTieneCompra }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "YaTieneCompra")
    public JAXBElement<YaTieneCompra> createYaTieneCompra(YaTieneCompra value) {
        return new JAXBElement<>(_YaTieneCompra_QNAME, YaTieneCompra.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtAsistente }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtAsistente }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtAsistente")
    public JAXBElement<DtAsistente> createDtAsistente(DtAsistente value) {
        return new JAXBElement<>(_DtAsistente_QNAME, DtAsistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtCompra }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtCompra }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtCompra")
    public JAXBElement<DtCompra> createDtCompra(DtCompra value) {
        return new JAXBElement<>(_DtCompra_QNAME, DtCompra.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtCompraWeb }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtCompraWeb }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtCompraWeb")
    public JAXBElement<DtCompraWeb> createDtCompraWeb(DtCompraWeb value) {
        return new JAXBElement<>(_DtCompraWeb_QNAME, DtCompraWeb.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtEdicion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtEdicion }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtEdicion")
    public JAXBElement<DtEdicion> createDtEdicion(DtEdicion value) {
        return new JAXBElement<>(_DtEdicion_QNAME, DtEdicion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtEvento }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtEvento }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtEvento")
    public JAXBElement<DtEvento> createDtEvento(DtEvento value) {
        return new JAXBElement<>(_DtEvento_QNAME, DtEvento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtOrganizador }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtOrganizador }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtOrganizador")
    public JAXBElement<DtOrganizador> createDtOrganizador(DtOrganizador value) {
        return new JAXBElement<>(_DtOrganizador_QNAME, DtOrganizador.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtPatrocinio }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtPatrocinio }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtPatrocinio")
    public JAXBElement<DtPatrocinio> createDtPatrocinio(DtPatrocinio value) {
        return new JAXBElement<>(_DtPatrocinio_QNAME, DtPatrocinio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtTipoRegistro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtTipoRegistro }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtTipoRegistro")
    public JAXBElement<DtTipoRegistro> createDtTipoRegistro(DtTipoRegistro value) {
        return new JAXBElement<>(_DtTipoRegistro_QNAME, DtTipoRegistro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtUsuario }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtUsuario }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "dtUsuario")
    public JAXBElement<DtUsuario> createDtUsuario(DtUsuario value) {
        return new JAXBElement<>(_DtUsuario_QNAME, DtUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExisteTipoRegistro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExisteTipoRegistro }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "existeTipoRegistro")
    public JAXBElement<ExisteTipoRegistro> createExisteTipoRegistro(ExisteTipoRegistro value) {
        return new JAXBElement<>(_ExisteTipoRegistro_QNAME, ExisteTipoRegistro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringArrayWrapper }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringArrayWrapper }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice.java.main/", name = "stringArray")
    public JAXBElement<StringArrayWrapper> createStringArray(StringArrayWrapper value) {
        return new JAXBElement<>(_StringArray_QNAME, StringArrayWrapper.class, null, value);
    }

}
