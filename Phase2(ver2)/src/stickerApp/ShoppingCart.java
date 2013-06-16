//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.16 um 10:44:43 PM CEST 
//


package stickerApp;

import java.math.BigDecimal;
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
 *         &lt;element ref="{}Products"/>
 *         &lt;element name="ShipTo" type="{}Adress"/>
 *         &lt;element ref="{}Price"/>
 *         &lt;element ref="{}Payment"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{}Currency use="required""/>
 *       &lt;attribute ref="{}ShoppingCart_ID use="required""/>
 *       &lt;attribute ref="{}Self use="required""/>
 *       &lt;attribute ref="{}Customer_ID use="required""/>
 *       &lt;attribute ref="{}Customer use="required""/>
 *       &lt;attribute ref="{}Shop_ID use="required""/>
 *       &lt;attribute ref="{}Shop use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "products",
    "shipTo",
    "price",
    "payment"
})
@XmlRootElement(name = "ShoppingCart")
public class ShoppingCart {

    @XmlElement(name = "Products", required = true)
    protected Products products;
    @XmlElement(name = "ShipTo", required = true)
    protected Adress shipTo;
    @XmlElement(name = "Price", required = true)
    protected BigDecimal price;
    @XmlElement(name = "Payment", required = true, defaultValue = "n.a.")
    protected String payment;
    @XmlAttribute(name = "Currency", required = true)
    protected String currency;
    @XmlAttribute(name = "ShoppingCart_ID", required = true)
    protected BigInteger shoppingCartID;
    @XmlAttribute(name = "Self", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String self;
    @XmlAttribute(name = "Customer_ID", required = true)
    protected BigInteger customerID;
    @XmlAttribute(name = "Customer", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String customer;
    @XmlAttribute(name = "Shop_ID", required = true)
    protected BigInteger shopID;
    @XmlAttribute(name = "Shop", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String shop;

    /**
     * Ruft den Wert der products-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Products }
     *     
     */
    public Products getProducts() {
        return products;
    }

    /**
     * Legt den Wert der products-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Products }
     *     
     */
    public void setProducts(Products value) {
        this.products = value;
    }

    /**
     * Ruft den Wert der shipTo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Adress }
     *     
     */
    public Adress getShipTo() {
        return shipTo;
    }

    /**
     * Legt den Wert der shipTo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Adress }
     *     
     */
    public void setShipTo(Adress value) {
        this.shipTo = value;
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
     * Ruft den Wert der payment-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayment() {
        return payment;
    }

    /**
     * Legt den Wert der payment-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayment(String value) {
        this.payment = value;
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
     * Ruft den Wert der shoppingCartID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getShoppingCartID() {
        return shoppingCartID;
    }

    /**
     * Legt den Wert der shoppingCartID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setShoppingCartID(BigInteger value) {
        this.shoppingCartID = value;
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
     * Ruft den Wert der customerID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCustomerID() {
        return customerID;
    }

    /**
     * Legt den Wert der customerID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCustomerID(BigInteger value) {
        this.customerID = value;
    }

    /**
     * Ruft den Wert der customer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Legt den Wert der customer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomer(String value) {
        this.customer = value;
    }

    /**
     * Ruft den Wert der shopID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getShopID() {
        return shopID;
    }

    /**
     * Legt den Wert der shopID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setShopID(BigInteger value) {
        this.shopID = value;
    }

    /**
     * Ruft den Wert der shop-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShop() {
        return shop;
    }

    /**
     * Legt den Wert der shop-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShop(String value) {
        this.shop = value;
    }

}
