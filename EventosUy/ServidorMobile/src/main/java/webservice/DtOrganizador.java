
package webservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtOrganizador complex type.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * 
 * <pre>{@code
 * <complexType name="dtOrganizador">
 *   <complexContent>
 *     <extension base="{http://webservice.java.main/}dtUsuario">
 *       <sequence>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         <element name="paginaWeb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         <element name="ediciones" type="{http://webservice.java.main/}dtEdicion" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtOrganizador", propOrder = {
    "descripcion",
    "paginaWeb",
    "ediciones"
})
public class DtOrganizador
    extends DtUsuario
{

    protected String descripcion;
    protected String paginaWeb;
    @XmlElement(nillable = true)
    protected List<DtEdicion> ediciones;

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad paginaWeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaginaWeb() {
        return paginaWeb;
    }

    /**
     * Define el valor de la propiedad paginaWeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaginaWeb(String value) {
        this.paginaWeb = value;
    }

    /**
     * Gets the value of the ediciones property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ediciones property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getEdiciones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtEdicion }
     * </p>
     * 
     * 
     * @return
     *     The value of the ediciones property.
     */
    public List<DtEdicion> getEdiciones() {
        if (ediciones == null) {
            ediciones = new ArrayList<>();
        }
        return this.ediciones;
    }

}
