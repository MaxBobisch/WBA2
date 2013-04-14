//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.04.10 um 03:57:56 PM CEST 
//


package rezeptSammlung;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{}name"/>
 *         &lt;element ref="{}zutaten"/>
 *         &lt;element ref="{}zubereitung"/>
 *         &lt;element ref="{}bilder" minOccurs="0"/>
 *         &lt;element ref="{}kommentare" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{}rezeptid"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "zutaten",
    "zubereitung",
    "bilder",
    "kommentare"
})
@XmlRootElement(name = "rezept")
public class Rezept {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected Zutaten zutaten;
    @XmlElement(required = true)
    protected Zubereitung zubereitung;
    protected Bilder bilder;
    protected Kommentare kommentare;
    @XmlAttribute(name = "rezeptid")
    protected BigInteger rezeptid;

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der zutaten-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Zutaten }
     *     
     */
    public Zutaten getZutaten() {
        return zutaten;
    }

    /**
     * Legt den Wert der zutaten-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Zutaten }
     *     
     */
    public void setZutaten(Zutaten value) {
        this.zutaten = value;
    }

    /**
     * Ruft den Wert der zubereitung-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Zubereitung }
     *     
     */
    public Zubereitung getZubereitung() {
        return zubereitung;
    }

    /**
     * Legt den Wert der zubereitung-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Zubereitung }
     *     
     */
    public void setZubereitung(Zubereitung value) {
        this.zubereitung = value;
    }

    /**
     * Ruft den Wert der bilder-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Bilder }
     *     
     */
    public Bilder getBilder() {
        return bilder;
    }

    /**
     * Legt den Wert der bilder-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Bilder }
     *     
     */
    public void setBilder(Bilder value) {
        this.bilder = value;
    }

    /**
     * Ruft den Wert der kommentare-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Kommentare }
     *     
     */
    public Kommentare getKommentare() {
        return kommentare;
    }

    /**
     * Legt den Wert der kommentare-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Kommentare }
     *     
     */
    public void setKommentare(Kommentare value) {
        this.kommentare = value;
    }

    /**
     * Ruft den Wert der rezeptid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRezeptid() {
        return rezeptid;
    }

    /**
     * Legt den Wert der rezeptid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRezeptid(BigInteger value) {
        this.rezeptid = value;
    }

}
