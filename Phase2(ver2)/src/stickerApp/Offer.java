//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.15 um 10:30:59 PM CEST 
//


package stickerApp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element ref="{}Name"/>
 *         &lt;element ref="{}Description"/>
 *         &lt;element ref="{}Item" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Price"/>
 *         &lt;element ref="{}Currency"/>
 *         &lt;element ref="{}Liker"/>
 *         &lt;element ref="{}Follower"/>
 *         &lt;element ref="{}Comments"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{}Offer_ID"/>
 *       &lt;attribute ref="{}User_ID"/>
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
    "item",
    "price",
    "currency",
    "liker",
    "follower",
    "comments"
})
@XmlRootElement(name = "Offer")
public class Offer {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Item")
    @XmlSchemaType(name = "anyURI")
    protected List<String> item;
    @XmlElement(name = "Price", required = true)
    protected BigDecimal price;
    @XmlElement(name = "Currency", required = true)
    protected String currency;
    @XmlElement(name = "Liker", required = true)
    protected Liker liker;
    @XmlElement(name = "Follower", required = true)
    protected Follower follower;
    @XmlElement(name = "Comments", required = true)
    protected Comments comments;
    @XmlAttribute(name = "Offer_ID")
    protected BigInteger offerID;
    @XmlAttribute(name = "User_ID")
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
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getItem() {
        if (item == null) {
            item = new ArrayList<String>();
        }
        return this.item;
    }

    /**
     * Ruft den Wert der price-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Legt den Wert der price-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    /**
     * Ruft den Wert der currency-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Legt den Wert der currency-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
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
     * Ruft den Wert der offerID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOfferID() {
        return offerID;
    }

    /**
     * Legt den Wert der offerID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOfferID(BigInteger value) {
        this.offerID = value;
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
