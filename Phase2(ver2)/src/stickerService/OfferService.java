package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ws.rs.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import stickerApp.*;

@Path( "/offers" )
public class OfferService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Offers.xml";
	static final String SERVERROOT = "localhost:4434";
	static ObjectFactory ob=new ObjectFactory();
	static final int USERID = 0;
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Offers, aus der XML Datei <~
	 */	
	private Offers xmlAuslesen() throws JAXBException, FileNotFoundException {
		Offers offers=ob.createOffers();
		JAXBContext context = JAXBContext.newInstance(Offers.class);
		Unmarshaller um = context.createUnmarshaller();
		offers = (Offers) um.unmarshal(new FileReader(XMLFILE));
		return offers;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Offers Offers -> Offers, die in die XML Datei geschrieben werden.)
	 */
	private void xmlSchreiben(Offers offers) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Offers.class);
		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		System.out.println("Ausgabe der gemarshalten Daten:");
		m.marshal(offers, System.out);
		
		Writer wr = null;
		try {
			wr = new FileWriter(XMLFILE);
			m.marshal(offers, wr);
		}
		finally {
			try {
				wr.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/* Zeigt alle Offer an.
	 * 		~> return-Wert: Alle Offer aus der XML Datei <~
	 */
	@GET
	@Produces ( " application/xml" )
	public Offers allOffers() throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Offers offers=xmlAuslesen();
		
		return offers;
	}
	
	/* Zeigt Offer mit OfferID an.
	 * 		~> return-Wert: Offer mit OfferID aus der XML Datei <~
	 */
	@GET
	@Path ("/{OfferID}")
	@Produces ( " application/xml" )
	public Offer oneOffer(@PathParam("Offer_ID") int OfferID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Offers offers=xmlAuslesen();
		Offer offer = offers.getOffer().get(OfferID);
		
		return offer;
	}
	
	/* Lösche Offer mit OfferID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{OfferID}")
	@Produces ( " application/xml" )
	public Offers deleteOffer(@PathParam("Offer_ID") int OfferID) throws JAXBException, IOException {
		//Hole XML Daten
		Offers offers=xmlAuslesen();
		Offer offer = offers.getOffer().remove(OfferID);
		xmlSchreiben(offers);
		return offers;
	}
	
	/* Erstelle Offer mit OfferID.
	 * 		~> return-Wert: Offer, das erstellt wurde <~
	 */
	@POST
	@Path ("/{OfferID}")
	@Produces ( " application/xml" )
	public Offer createOffer(@PathParam("Offer_ID") int OfferID, 
			String title, 
			String description,
			double price,
			String currency
			) throws JAXBException, IOException {
		//Hole XML Daten
		Offers offers=xmlAuslesen();
		//neue Offer
		Offer offer = new Offer();
		int lastindex = offers.getOffer().lastIndexOf(offer);
		lastindex++;
		BigInteger ID = new BigInteger("" + lastindex);
		offer.setOfferID(ID);
		offer.setTitle(title);
		offer.setDescription(description);
		offer.setPrice(new BigDecimal("" + price));
		offer.setCurrency(currency);
		Liker liker = new Liker();
		Follower follower = new Follower();
		Comments comments = new Comments();
		offer.setLiker(liker);
		offer.setFollower(follower);
		offer.setComments(comments);
		offer.setSelf(SERVERROOT + "/offers/" + lastindex);
		offer.setUserID(new BigInteger ("" + USERID));
		offer.setOwner(SERVERROOT + "/users/" + USERID);
		//zur Offersliste hinzufuegen
		offers.getOffer().add(lastindex, offer);
		//ins XML zurückschreiben
		xmlSchreiben(offers);
		return offer;
	}
	
}