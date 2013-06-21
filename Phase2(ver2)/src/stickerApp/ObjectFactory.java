//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.21 um 08:34:28 PM CEST 
//


package stickerApp;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the stickerApp package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PhotoLink_QNAME = new QName("", "PhotoLink");
    private final static QName _Street_QNAME = new QName("", "Street");
    private final static QName _Count_QNAME = new QName("", "Count");
    private final static QName _Telephone_QNAME = new QName("", "Telephone");
    private final static QName _Payment_QNAME = new QName("", "Payment");
    private final static QName _Datetime_QNAME = new QName("", "Datetime");
    private final static QName _Item_QNAME = new QName("", "Item");
    private final static QName _Nickname_QNAME = new QName("", "Nickname");
    private final static QName _RelatedSticker_QNAME = new QName("", "RelatedSticker");
    private final static QName _FirstName_QNAME = new QName("", "FirstName");
    private final static QName _Longitude_QNAME = new QName("", "longitude");
    private final static QName _FamilyName_QNAME = new QName("", "FamilyName");
    private final static QName _Description_QNAME = new QName("", "Description");
    private final static QName _Number_QNAME = new QName("", "Number");
    private final static QName _Country_QNAME = new QName("", "Country");
    private final static QName _Province_QNAME = new QName("", "Province");
    private final static QName _Title_QNAME = new QName("", "Title");
    private final static QName _City_QNAME = new QName("", "City");
    private final static QName _Text_QNAME = new QName("", "Text");
    private final static QName _Link_QNAME = new QName("", "Link");
    private final static QName _PostalCode_QNAME = new QName("", "PostalCode");
    private final static QName _Email_QNAME = new QName("", "email");
    private final static QName _Price_QNAME = new QName("", "Price");
    private final static QName _Gender_QNAME = new QName("", "Gender");
    private final static QName _Latitude_QNAME = new QName("", "latitude");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: stickerApp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Adress }
     * 
     */
    public Adress createAdress() {
        return new Adress();
    }

    /**
     * Create an instance of {@link StickerContainer }
     * 
     */
    public StickerContainer createStickerContainer() {
        return new StickerContainer();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link CollectionContainer }
     * 
     */
    public CollectionContainer createCollectionContainer() {
        return new CollectionContainer();
    }

    /**
     * Create an instance of {@link PhotoContainer }
     * 
     */
    public PhotoContainer createPhotoContainer() {
        return new PhotoContainer();
    }

    /**
     * Create an instance of {@link SinglePhoto }
     * 
     */
    public SinglePhoto createSinglePhoto() {
        return new SinglePhoto();
    }

    /**
     * Create an instance of {@link OfferContainer }
     * 
     */
    public OfferContainer createOfferContainer() {
        return new OfferContainer();
    }

    /**
     * Create an instance of {@link SingleOffer }
     * 
     */
    public SingleOffer createSingleOffer() {
        return new SingleOffer();
    }

    /**
     * Create an instance of {@link ShoppingCartContainer }
     * 
     */
    public ShoppingCartContainer createShoppingCartContainer() {
        return new ShoppingCartContainer();
    }

    /**
     * Create an instance of {@link SingleShoppingCart }
     * 
     */
    public SingleShoppingCart createSingleShoppingCart() {
        return new SingleShoppingCart();
    }

    /**
     * Create an instance of {@link Liker }
     * 
     */
    public Liker createLiker() {
        return new Liker();
    }

    /**
     * Create an instance of {@link Follower }
     * 
     */
    public Follower createFollower() {
        return new Follower();
    }

    /**
     * Create an instance of {@link Comments }
     * 
     */
    public Comments createComments() {
        return new Comments();
    }

    /**
     * Create an instance of {@link Comment }
     * 
     */
    public Comment createComment() {
        return new Comment();
    }

    /**
     * Create an instance of {@link Users }
     * 
     */
    public Users createUsers() {
        return new Users();
    }

    /**
     * Create an instance of {@link Products }
     * 
     */
    public Products createProducts() {
        return new Products();
    }

    /**
     * Create an instance of {@link Sticker }
     * 
     */
    public Sticker createSticker() {
        return new Sticker();
    }

    /**
     * Create an instance of {@link RelatedPhotos }
     * 
     */
    public RelatedPhotos createRelatedPhotos() {
        return new RelatedPhotos();
    }

    /**
     * Create an instance of {@link Offer }
     * 
     */
    public Offer createOffer() {
        return new Offer();
    }

    /**
     * Create an instance of {@link Collections }
     * 
     */
    public Collections createCollections() {
        return new Collections();
    }

    /**
     * Create an instance of {@link Collection }
     * 
     */
    public Collection createCollection() {
        return new Collection();
    }

    /**
     * Create an instance of {@link Photo }
     * 
     */
    public Photo createPhoto() {
        return new Photo();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link Offers }
     * 
     */
    public Offers createOffers() {
        return new Offers();
    }

    /**
     * Create an instance of {@link ShoppingCart }
     * 
     */
    public ShoppingCart createShoppingCart() {
        return new ShoppingCart();
    }

    /**
     * Create an instance of {@link Stickers }
     * 
     */
    public Stickers createStickers() {
        return new Stickers();
    }

    /**
     * Create an instance of {@link Photos }
     * 
     */
    public Photos createPhotos() {
        return new Photos();
    }

    /**
     * Create an instance of {@link ShoppingCarts }
     * 
     */
    public ShoppingCarts createShoppingCarts() {
        return new ShoppingCarts();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PhotoLink")
    public JAXBElement<String> createPhotoLink(String value) {
        return new JAXBElement<String>(_PhotoLink_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Street")
    public JAXBElement<String> createStreet(String value) {
        return new JAXBElement<String>(_Street_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Count")
    public JAXBElement<BigInteger> createCount(BigInteger value) {
        return new JAXBElement<BigInteger>(_Count_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Telephone")
    public JAXBElement<String> createTelephone(String value) {
        return new JAXBElement<String>(_Telephone_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Payment", defaultValue = "n.a.")
    public JAXBElement<String> createPayment(String value) {
        return new JAXBElement<String>(_Payment_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Datetime")
    public JAXBElement<XMLGregorianCalendar> createDatetime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_Datetime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Item")
    public JAXBElement<String> createItem(String value) {
        return new JAXBElement<String>(_Item_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Nickname")
    public JAXBElement<String> createNickname(String value) {
        return new JAXBElement<String>(_Nickname_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RelatedSticker")
    public JAXBElement<String> createRelatedSticker(String value) {
        return new JAXBElement<String>(_RelatedSticker_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FirstName")
    public JAXBElement<String> createFirstName(String value) {
        return new JAXBElement<String>(_FirstName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "longitude")
    public JAXBElement<BigDecimal> createLongitude(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Longitude_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FamilyName")
    public JAXBElement<String> createFamilyName(String value) {
        return new JAXBElement<String>(_FamilyName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description")
    public JAXBElement<String> createDescription(String value) {
        return new JAXBElement<String>(_Description_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Number")
    public JAXBElement<String> createNumber(String value) {
        return new JAXBElement<String>(_Number_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Country")
    public JAXBElement<String> createCountry(String value) {
        return new JAXBElement<String>(_Country_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Province")
    public JAXBElement<String> createProvince(String value) {
        return new JAXBElement<String>(_Province_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Title")
    public JAXBElement<String> createTitle(String value) {
        return new JAXBElement<String>(_Title_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "City")
    public JAXBElement<String> createCity(String value) {
        return new JAXBElement<String>(_City_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Text")
    public JAXBElement<String> createText(String value) {
        return new JAXBElement<String>(_Text_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Link")
    public JAXBElement<String> createLink(String value) {
        return new JAXBElement<String>(_Link_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PostalCode")
    public JAXBElement<String> createPostalCode(String value) {
        return new JAXBElement<String>(_PostalCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "email")
    public JAXBElement<String> createEmail(String value) {
        return new JAXBElement<String>(_Email_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Price")
    public JAXBElement<BigDecimal> createPrice(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Price_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Gender", defaultValue = "not specified")
    public JAXBElement<String> createGender(String value) {
        return new JAXBElement<String>(_Gender_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "latitude")
    public JAXBElement<BigDecimal> createLatitude(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Latitude_QNAME, BigDecimal.class, null, value);
    }

}
