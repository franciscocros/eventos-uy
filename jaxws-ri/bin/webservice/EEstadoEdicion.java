
package webservice;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Clase Java para eEstadoEdicion.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * <pre>{@code
 * <simpleType name="eEstadoEdicion">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="INGRESADA"/>
 *     <enumeration value="CONFIRMADA"/>
 *     <enumeration value="RECHAZADA"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "eEstadoEdicion")
@XmlEnum
public enum EEstadoEdicion {

    INGRESADA,
    CONFIRMADA,
    RECHAZADA;

    public String value() {
        return name();
    }

    public static EEstadoEdicion fromValue(String v) {
        return valueOf(v);
    }

}
