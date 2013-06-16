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
 *         &lt;element ref="{}Nickname"/>
 *         &lt;element name="HomeAdress" type="{}Adress" minOccurs="0"/>
 *         &lt;element name="ShopAdress" type="{}Adress" minOccurs="0"/>
 *         &lt;element ref="{}Stickers"/>
 *         &lt;element ref="{}Collections"/>
 *         &lt;element ref="{}Photos"/>
 *         &lt;element ref="{}Offers"/>
 *         &lt;element ref="{}Inventory"/>
 *         &lt;element ref="{}Liker"/>
 *         &lt;element ref="{}Follower"/>
 *         &lt;element ref="{}Comments"/>
 *       &lt;/sequence>
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
    "nickname",
    "homeAdress",
    "shopAdress",
    "stickers",
    "collections",
    "photos",
    "offers",
    "inventory",
    "liker",
    "follower",
    "comments"
})
@XmlRootElement(name = "User")
public class User {

    @XmlElement(name = "Nickname", required = true)
    protected String nickname;
    @XmlElement(name = "HomeAdress")
    protected Adress homeAdress;
    @XmlElement(name = "ShopAdress")
    protected Adress shopAdress;
    @XmlElement(name = "Stickers", required = true)
    protected Stickers stickers;
    @XmlElement(name = "Collections", required = true)
    protected Collections collections;
    @XmlElement(name = "Photos", required = true)
    protected Photos photos;
    @XmlElement(name = "Offers", required = true)
    protected Offers offers;
    @XmlElement(name = "Inventory", required = true)
    protected Inventory inventory;
    @XmlElement(name = "Liker", required = true)
    protected Liker liker;
    @XmlElement(name = "Follower", required = true)
    protected Follower follower;
    @XmlElement(name = "Comments", required = true)
    protected Comments comments;
    @XmlAttribute(name = "User_ID", required = true)
    protected BigInteger userID;
    @XmlAttribute(name = "Self", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String self;

    /**
     * Ruft den Wert der nickname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Legt den Wert der nickname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Ruft den Wert der homeAdress-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Adress }
     *     
     */
    public Adress getHomeAdress() {
        return homeAdress;
    }

    /**
     * Legt den Wert der homeAdress-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Adress }
     *     
     */
    public void setHomeAdress(Adress value) {
        this.homeAdress = value;
    }

    /**
     * Ruft den Wert der shopAdress-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Adress }
     *     
     */
    public Adress getShopAdress() {
        return shopAdress;
    }

    /**
     * Legt den Wert der shopAdress-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Adress }
     *     
     */
    public void setShopAdress(Adress value) {
        this.shopAdress = value;
    }

    /**
     * Ruft den Wert der stickers-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Stickers }
     *     
     */
    public Stickers getStickers() {
        return stickers;
    }

    /**
     * Legt den Wert der stickers-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Stickers }
     *     
     */
    public void setStickers(Stickers value) {
        this.stickers = value;
    }

    /**
     * Ruft den Wert der collections-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Collections }
     *     
     */
    public Collections getCollections() {
        return collections;
    }

    /**
     * Legt den Wert der collections-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Collections }
     *     
     */
    public void setCollections(Collections value) {
        this.collections = value;
    }

    /**
     * Ruft den Wert der photos-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Photos }
     *     
     */
    public Photos getPhotos() {
        return photos;
    }

    /**
     * Legt den Wert der photos-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Photos }
     *     
     */
    public void setPhotos(Photos value) {
        this.photos = value;
    }

    /**
     * Ruft den Wert der offers-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Offers }
     *     
     */
    public Offers getOffers() {
        return offers;
    }

    /**
     * Legt den Wert der offers-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Offers }
     *     
     */
    public void setOffers(Offers value) {
        this.offers = value;
    }

    /**
     * Ruft den Wert der inventory-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Inventory }
     *     
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Legt den Wert der inventory-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Inventory }
     *     
     */
    public void setInventory(Inventory value) {
        this.inventory = value;
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
