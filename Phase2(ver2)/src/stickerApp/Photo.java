//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.19 um 06:35:20 PM CEST 
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
 *         &lt;element ref="{}Title"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Datetime"/>
 *         &lt;element ref="{}PhotoLink"/>
 *         &lt;element ref="{}Location" minOccurs="0"/>
 *         &lt;element ref="{}RelatedSticker" minOccurs="0"/>
 *         &lt;element ref="{}Liker"/>
 *         &lt;element ref="{}Follower"/>
 *         &lt;element ref="{}Comments"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{}Photo_ID use="required""/>
 *       &lt;attribute ref="{}Self use="required""/>
 *       &lt;attribute ref="{}User_ID use="required""/>
 *       &lt;attribute ref="{}Owner use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "title",
    "description",
    "datetime",
    "photoLink",
    "location",
    "relatedSticker",
    "liker",
    "follower",
    "comments"
})
@XmlRootElement(name = "Photo")
public class Photo {

    @XmlElement(name = "Title", required = true)
    protected String title;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Datetime", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datetime;
    @XmlElement(name = "PhotoLink", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String photoLink;
    @XmlElement(name = "Location")
    protected Location location;
    @XmlElement(name = "RelatedSticker")
    @XmlSchemaType(name = "anyURI")
    protected String relatedSticker;
    @XmlElement(name = "Liker", required = true)
    protected Liker liker;
    @XmlElement(name = "Follower", required = true)
    protected Follower follower;
    @XmlElement(name = "Comments", required = true)
    protected Comments comments;
    @XmlAttribute(name = "Photo_ID", required = true)
    protected BigInteger photoID;
    @XmlAttribute(name = "Self", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String self;
    @XmlAttribute(name = "User_ID", required = true)
    protected BigInteger userID;
    @XmlAttribute(name = "Owner", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String owner;

    /**
     * Ruft den Wert der title-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Legt den Wert der title-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
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
     * Ruft den Wert der datetime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatetime() {
        return datetime;
    }

    /**
     * Legt den Wert der datetime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatetime(XMLGregorianCalendar value) {
        this.datetime = value;
    }

    /**
     * Ruft den Wert der photoLink-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhotoLink() {
        return photoLink;
    }

    /**
     * Legt den Wert der photoLink-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhotoLink(String value) {
        this.photoLink = value;
    }

    /**
     * Ruft den Wert der location-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Legt den Wert der location-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setLocation(Location value) {
        this.location = value;
    }

    /**
     * Ruft den Wert der relatedSticker-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedSticker() {
        return relatedSticker;
    }

    /**
     * Legt den Wert der relatedSticker-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedSticker(String value) {
        this.relatedSticker = value;
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
     * Ruft den Wert der photoID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPhotoID() {
        return photoID;
    }

    /**
     * Legt den Wert der photoID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPhotoID(BigInteger value) {
        this.photoID = value;
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
     * Ruft den Wert der owner-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Legt den Wert der owner-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

}
