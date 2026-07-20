
package StringArray.xjb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtPatrocinio complex type.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * 
 * <pre>{@code
 * <complexType name="dtPatrocinio">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="aporteEconomico" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fecha" type="{http://webservice.java.main/}localDate" minOccurs="0"/>
 *         <element name="cantGratis" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="nivel" type="{http://webservice.java.main/}eNivelPatrocinio" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPatrocinio", propOrder = {
    "aporteEconomico",
    "codigo",
    "fecha",
    "cantGratis",
    "nivel"
})
public class DtPatrocinio {

    protected float aporteEconomico;
    protected String codigo;
    protected LocalDate fecha;
    protected int cantGratis;
    @XmlSchemaType(name = "string")
    protected ENivelPatrocinio nivel;

    /**
     * Obtiene el valor de la propiedad aporteEconomico.
     * 
     */
    public float getAporteEconomico() {
        return aporteEconomico;
    }

    /**
     * Define el valor de la propiedad aporteEconomico.
     * 
     */
    public void setAporteEconomico(float value) {
        this.aporteEconomico = value;
    }

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFecha(LocalDate value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad cantGratis.
     * 
     */
    public int getCantGratis() {
        return cantGratis;
    }

    /**
     * Define el valor de la propiedad cantGratis.
     * 
     */
    public void setCantGratis(int value) {
        this.cantGratis = value;
    }

    /**
     * Obtiene el valor de la propiedad nivel.
     * 
     * @return
     *     possible object is
     *     {@link ENivelPatrocinio }
     *     
     */
    public ENivelPatrocinio getNivel() {
        return nivel;
    }

    /**
     * Define el valor de la propiedad nivel.
     * 
     * @param value
     *     allowed object is
     *     {@link ENivelPatrocinio }
     *     
     */
    public void setNivel(ENivelPatrocinio value) {
        this.nivel = value;
    }

}
