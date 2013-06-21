//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.21 um 05:50:54 PM CEST 
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
 *         &lt;element ref="{}Gender"/>
 *         &lt;element name="HomeAdress" type="{}Adress" minOccurs="0"/>
 *         &lt;element name="ShopAdress" type="{}Adress" minOccurs="0"/>
 *         &lt;element ref="{}StickerContainer"/>
 *         &lt;element ref="{}CollectionContainer"/>
 *         &lt;element ref="{}PhotoContainer"/>
 *         &lt;element ref="{}OfferContainer"/>
 *         &lt;element ref="{}ShoppingCartContainer"/>
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
    "gender",
    "homeAdress",
    "shopAdress",
    "stickerContainer",
    "collectionContainer",
    "photoContainer",
    "offerContainer",
    "shoppingCartContainer",
    "liker",
    "follower",
    "comments"
})
@XmlRootElement(name = "User")
public class User {

    @XmlElement(name = "Nickname", required = true)
    protected String nickname;
    @XmlElement(name = "Gender", required = true, defaultValue = "not specified")
    protected String gender;
    @XmlElement(name = "HomeAdress")
    protected Adress homeAdress;
    @XmlElement(name = "ShopAdress")
    protected Adress shopAdress;
    @XmlElement(name = "StickerContainer", required = true)
    protected StickerContainer stickerContainer;
    @XmlElement(name = "CollectionContainer", required = true)
    protected CollectionContainer collectionContainer;
    @XmlElement(name = "PhotoContainer", required = true)
    protected PhotoContainer photoContainer;
    @XmlElement(name = "OfferContainer", required = true)
    protected OfferContainer offerContainer;
    @XmlElement(name = "ShoppingCartContainer", required = true)
    protected ShoppingCartContainer shoppingCartContainer;
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
     * Ruft den Wert der gender-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Legt den Wert der gender-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
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
     * Ruft den Wert der stickerContainer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link StickerContainer }
     *     
     */
    public StickerContainer getStickerContainer() {
        return stickerContainer;
    }

    /**
     * Legt den Wert der stickerContainer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link StickerContainer }
     *     
     */
    public void setStickerContainer(StickerContainer value) {
        this.stickerContainer = value;
    }

    /**
     * Ruft den Wert der collectionContainer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CollectionContainer }
     *     
     */
    public CollectionContainer getCollectionContainer() {
        return collectionContainer;
    }

    /**
     * Legt den Wert der collectionContainer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CollectionContainer }
     *     
     */
    public void setCollectionContainer(CollectionContainer value) {
        this.collectionContainer = value;
    }

    /**
     * Ruft den Wert der photoContainer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PhotoContainer }
     *     
     */
    public PhotoContainer getPhotoContainer() {
        return photoContainer;
    }

    /**
     * Legt den Wert der photoContainer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PhotoContainer }
     *     
     */
    public void setPhotoContainer(PhotoContainer value) {
        this.photoContainer = value;
    }

    /**
     * Ruft den Wert der offerContainer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link OfferContainer }
     *     
     */
    public OfferContainer getOfferContainer() {
        return offerContainer;
    }

    /**
     * Legt den Wert der offerContainer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferContainer }
     *     
     */
    public void setOfferContainer(OfferContainer value) {
        this.offerContainer = value;
    }

    /**
     * Ruft den Wert der shoppingCartContainer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ShoppingCartContainer }
     *     
     */
    public ShoppingCartContainer getShoppingCartContainer() {
        return shoppingCartContainer;
    }

    /**
     * Legt den Wert der shoppingCartContainer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ShoppingCartContainer }
     *     
     */
    public void setShoppingCartContainer(ShoppingCartContainer value) {
        this.shoppingCartContainer = value;
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
