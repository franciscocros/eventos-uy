
package webservice;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Clase Java para eNivelPatrocinio.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * <pre>{@code
 * <simpleType name="eNivelPatrocinio">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="PLATINO"/>
 *     <enumeration value="ORO"/>
 *     <enumeration value="PLATA"/>
 *     <enumeration value="BRONCE"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "eNivelPatrocinio")
@XmlEnum
public enum ENivelPatrocinio {

    PLATINO,
    ORO,
    PLATA,
    BRONCE;

    public String value() {
        return name();
    }

    public static ENivelPatrocinio fromValue(String v) {
        return valueOf(v);
    }

}
