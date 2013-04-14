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
 *         &lt;element ref="{}arbeitszeit"/>
 *         &lt;element ref="{}schwierigkeitsgrad"/>
 *         &lt;element ref="{}brennwert"/>
 *         &lt;element ref="{}anleitung"/>
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
    "arbeitszeit",
    "schwierigkeitsgrad",
    "brennwert",
    "anleitung"
})
@XmlRootElement(name = "zubereitung")
public class Zubereitung {

    @XmlElement(required = true)
    protected Arbeitszeit arbeitszeit;
    @XmlElement(required = true)
    protected String schwierigkeitsgrad;
    @XmlElement(required = true)
    protected Brennwert brennwert;
    @XmlElement(required = true)
    protected Anleitung anleitung;

    /**
     * Ruft den Wert der arbeitszeit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Arbeitszeit }
     *     
     */
    public Arbeitszeit getArbeitszeit() {
        return arbeitszeit;
    }

    /**
     * Legt den Wert der arbeitszeit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Arbeitszeit }
     *     
     */
    public void setArbeitszeit(Arbeitszeit value) {
        this.arbeitszeit = value;
    }

    /**
     * Ruft den Wert der schwierigkeitsgrad-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchwierigkeitsgrad() {
        return schwierigkeitsgrad;
    }

    /**
     * Legt den Wert der schwierigkeitsgrad-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchwierigkeitsgrad(String value) {
        this.schwierigkeitsgrad = value;
    }

    /**
     * Ruft den Wert der brennwert-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Brennwert }
     *     
     */
    public Brennwert getBrennwert() {
        return brennwert;
    }

    /**
     * Legt den Wert der brennwert-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Brennwert }
     *     
     */
    public void setBrennwert(Brennwert value) {
        this.brennwert = value;
    }

    /**
     * Ruft den Wert der anleitung-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Anleitung }
     *     
     */
    public Anleitung getAnleitung() {
        return anleitung;
    }

    /**
     * Legt den Wert der anleitung-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Anleitung }
     *     
     */
    public void setAnleitung(Anleitung value) {
        this.anleitung = value;
    }

}
