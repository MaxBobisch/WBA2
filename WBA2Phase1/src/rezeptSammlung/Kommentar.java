//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.04.10 um 03:57:56 PM CEST 
//


package rezeptSammlung;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}inhalt"/>
 *         &lt;element ref="{}quelle"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "inhalt",
    "quelle"
})
@XmlRootElement(name = "kommentar")
public class Kommentar {

    @XmlElement(required = true)
    protected String inhalt;
    @XmlElement(required = true)
    protected Quelle quelle;

    /**
     * Ruft den Wert der inhalt-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInhalt() {
        return inhalt;
    }

    /**
     * Legt den Wert der inhalt-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInhalt(String value) {
        this.inhalt = value;
    }

    /**
     * Ruft den Wert der quelle-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Quelle }
     *     
     */
    public Quelle getQuelle() {
        return quelle;
    }

    /**
     * Legt den Wert der quelle-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Quelle }
     *     
     */
    public void setQuelle(Quelle value) {
        this.quelle = value;
    }

}
