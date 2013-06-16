//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.15 um 10:30:59 PM CEST 
//


package stickerApp;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element ref="{}Name" minOccurs="0"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Date" minOccurs="0"/>
 *         &lt;element ref="{}RelatedPhotos"/>
 *         &lt;element ref="{}Liker"/>
 *         &lt;element ref="{}Follower"/>
 *         &lt;element ref="{}Comments"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{}Sticker_ID use="required""/>
 *       &lt;attribute ref="{}User_ID use="required""/>
 *       &lt;attribute ref="{}Self use="required""/>
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
    "description",
    "date",
    "relatedPhotos",
    "liker",
    "follower",
    "comments"
})
@XmlRootElement(name = "Sticker")
public class Sticker {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "RelatedPhotos", required = true)
    protected RelatedPhotos relatedPhotos;
    @XmlElement(name = "Liker", required = true)
    protected Liker liker;
    @XmlElement(name = "Follower", required = true)
    protected Follower follower;
    @XmlElement(name = "Comments", required = true)
    protected Comments comments;
    @XmlAttribute(name = "Sticker_ID", required = true)
    protected BigInteger stickerID;
    @XmlAttribute(name = "User_ID", required = true)
    protected BigInteger userID;
    @XmlAttribute(name = "Self", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String self;

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
     * Ruft den Wert der description-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Legt den Wert der description-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Ruft den Wert der date-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Legt den Wert der date-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Ruft den Wert der relatedPhotos-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link RelatedPhotos }
     *     
     */
    public RelatedPhotos getRelatedPhotos() {
        return relatedPhotos;
    }

    /**
     * Legt den Wert der relatedPhotos-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatedPhotos }
     *     
     */
    public void setRelatedPhotos(RelatedPhotos value) {
        this.relatedPhotos = value;
    }

    /**
     * Ruft den Wert der liker-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Liker }
     *     
     */
    public Liker getLiker() {
        return liker;
    }

    /**
     * Legt den Wert der liker-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Liker }
     *     
     */
    public void setLiker(Liker value) {
        this.liker = value;
    }

    /**
     * Ruft den Wert der follower-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Follower }
     *     
     */
    public Follower getFollower() {
        return follower;
    }

    /**
     * Legt den Wert der follower-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Follower }
     *     
     */
    public void setFollower(Follower value) {
        this.follower = value;
    }

    /**
     * Ruft den Wert der comments-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Comments }
     *     
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Legt den Wert der comments-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Comments }
     *     
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Ruft den Wert der stickerID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStickerID() {
        return stickerID;
    }

    /**
     * Legt den Wert der stickerID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStickerID(BigInteger value) {
        this.stickerID = value;
    }

    /**
     * Ruft den Wert der userID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUserID() {
        return userID;
    }

    /**
     * Legt den Wert der userID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUserID(BigInteger value) {
        this.userID = value;
    }

    /**
     * Ruft den Wert der self-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelf() {
        return self;
    }

    /**
     * Legt den Wert der self-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelf(String value) {
        this.self = value;
    }

}
