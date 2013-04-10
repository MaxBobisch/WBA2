//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.04.10 um 03:57:56 PM CEST 
//


package rezeptSammlung;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

    private final static QName _Name_QNAME = new QName("", "name");
    private final static QName _Inhalt_QNAME = new QName("", "inhalt");
    private final static QName _Schwierigkeitsgrad_QNAME = new QName("", "schwierigkeitsgrad");
    private final static QName _AnzahlPersonen_QNAME = new QName("", "anzahl_personen");
    private final static QName _Datum_QNAME = new QName("", "datum");
    private final static QName _Avatar_QNAME = new QName("", "avatar");
    private final static QName _Bildbesitzer_QNAME = new QName("", "bildbesitzer");
    private final static QName _Url_QNAME = new QName("", "url");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Brennwert }
     * 
     */
    public Brennwert createBrennwert() {
        return new Brennwert();
    }

    /**
     * Create an instance of {@link Rezept }
     * 
     */
    public Rezept createRezept() {
        return new Rezept();
    }

    /**
     * Create an instance of {@link Zutaten }
     * 
     */
    public Zutaten createZutaten() {
        return new Zutaten();
    }

    /**
     * Create an instance of {@link Zutat }
     * 
     */
    public Zutat createZutat() {
        return new Zutat();
    }

    /**
     * Create an instance of {@link Zubereitung }
     * 
     */
    public Zubereitung createZubereitung() {
        return new Zubereitung();
    }

    /**
     * Create an instance of {@link Arbeitszeit }
     * 
     */
    public Arbeitszeit createArbeitszeit() {
        return new Arbeitszeit();
    }

    /**
     * Create an instance of {@link Anleitung }
     * 
     */
    public Anleitung createAnleitung() {
        return new Anleitung();
    }

    /**
     * Create an instance of {@link Schritt }
     * 
     */
    public Schritt createSchritt() {
        return new Schritt();
    }

    /**
     * Create an instance of {@link Bilder }
     * 
     */
    public Bilder createBilder() {
        return new Bilder();
    }

    /**
     * Create an instance of {@link Bild }
     * 
     */
    public Bild createBild() {
        return new Bild();
    }

    /**
     * Create an instance of {@link Kommentare }
     * 
     */
    public Kommentare createKommentare() {
        return new Kommentare();
    }

    /**
     * Create an instance of {@link Kommentar }
     * 
     */
    public Kommentar createKommentar() {
        return new Kommentar();
    }

    /**
     * Create an instance of {@link Quelle }
     * 
     */
    public Quelle createQuelle() {
        return new Quelle();
    }

    /**
     * Create an instance of {@link Autor }
     * 
     */
    public Autor createAutor() {
        return new Autor();
    }

    /**
     * Create an instance of {@link Rezeptsammlung }
     * 
     */
    public Rezeptsammlung createRezeptsammlung() {
        return new Rezeptsammlung();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "inhalt")
    public JAXBElement<String> createInhalt(String value) {
        return new JAXBElement<String>(_Inhalt_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "schwierigkeitsgrad")
    public JAXBElement<String> createSchwierigkeitsgrad(String value) {
        return new JAXBElement<String>(_Schwierigkeitsgrad_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "anzahl_personen")
    public JAXBElement<BigInteger> createAnzahlPersonen(BigInteger value) {
        return new JAXBElement<BigInteger>(_AnzahlPersonen_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "datum")
    public JAXBElement<XMLGregorianCalendar> createDatum(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_Datum_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "avatar")
    public JAXBElement<String> createAvatar(String value) {
        return new JAXBElement<String>(_Avatar_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "bildbesitzer")
    public JAXBElement<String> createBildbesitzer(String value) {
        return new JAXBElement<String>(_Bildbesitzer_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "url")
    public JAXBElement<String> createUrl(String value) {
        return new JAXBElement<String>(_Url_QNAME, String.class, null, value);
    }

}
