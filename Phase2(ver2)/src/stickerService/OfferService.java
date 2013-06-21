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
import javax.xml.datatype.DatatypeConfigurationException;

import stickerApp.*;

@Path( "/offers" )
public class OfferService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Offers.xml";
	static ObjectFactory ob=new ObjectFactory();
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Offers, aus der XML Datei <~
	 */	
	protected Offers xmlAuslesen() throws JAXBException, FileNotFoundException {
		Offers offers=ob.createOffers();
		JAXBContext context = JAXBContext.newInstance(Offers.class);
		Unmarshaller um = context.createUnmarshaller();
		offers = (Offers) um.unmarshal(new FileReader(XMLFILE));
		return offers;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Offers Offers -> Offers, die in die XML Datei geschrieben werden.)
	 */
	protected void xmlSchreiben(Offers offers) throws JAXBException, IOException {
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
			java.util.ListIterator<Offer> iterator = offers.getOffer().listIterator();
			while(iterator.hasNext()) {
				if(OfferID == iterator.next().getOfferID().intValue()) {
					return iterator.next();
				}
				iterator.next();
			}
			return null;
	}
	
	/* Lösche Offer mit OfferID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{OfferID}")
	@Produces ( " application/xml" )
	public Offers deleteOffer(@PathParam("Offer_ID") int offerID) throws JAXBException, IOException {
		//Hole XML Daten
		Offers offers=xmlAuslesen();
		java.util.ListIterator<Offer> iterator = offers.getOffer().listIterator();
		while(iterator.hasNext()) {
			if(offerID == iterator.next().getOfferID().intValue()) {
				offers.getOffer().remove(iterator.nextIndex());
			}
			iterator.next();
		}
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
		int id = 0;
		for(Offer c : offers.getOffer()) {
			if(id <= c.getOfferID().intValue()) {
				id = c.getOfferID().intValue() + 1;
			}
		}
		offer.setOfferID(new BigInteger(""+id));
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
		offer.setSelf(Helper.SERVERROOT + "/offers/" + id);
		offer.setUserID(new BigInteger ("" + Helper.USERID));
		offer.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		//zur Offersliste hinzufuegen
		offers.getOffer().add(offer);
		//ins XML zurückschreiben
		xmlSchreiben(offers);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		java.util.ListIterator<User> userIterator = users.getUser().listIterator();
		int userindex=0;
		SingleOffer singleOffer = new SingleOffer();
		singleOffer.setTitle(title);
		singleOffer.setPrice(new BigDecimal("" + price));
		singleOffer.setCurrency(currency);
		singleOffer.setLink(Helper.SERVERROOT + "/offers/" + id);
		while(userIterator.hasNext()) {
			if(Helper.USERID == userIterator.next().getUserID().intValue()) {
				userindex = userIterator.nextIndex();
				users.getUser().get(userindex).getOfferContainer().getSingleOffer().add(singleOffer);
				US.xmlSchreiben(users);
			}
			userIterator.next();
		}
		
		return offer;
	}
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
//	@POST
//	@Path ("/{OfferID}")
//	@Produces ( " application/xml" )
	public Comments addComment(@PathParam("Offer_ID") int OfferID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Offers offers = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/offers/" + Helper.USERID);
		comment.setText(text);
		
		java.util.ListIterator<Offer> iterator = offers.getOffer().listIterator();
		int commentID = 0;
		int index=0;
		while(iterator.hasNext()) {
			if(OfferID == iterator.next().getOfferID().intValue()) {
				for(Comment c : iterator.next().getComments().getComment()) {
					if (commentID <= c.getCommentID().intValue())
						commentID = c.getCommentID().intValue() + 1;
						index=iterator.nextIndex();
				}
				comment.setCommentID(new BigInteger("" + commentID));
				offers.getOffer().get(iterator.nextIndex()).getComments().getComment().add(comment);
				xmlSchreiben(offers);
			}
			iterator.next();
		}		
		return offers.getOffer().get(index).getComments();
	}
	
	/*
	 * Füge Liker zu Offer hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{OfferID}")
//	@Produces ( " application/xml" )
	public Liker addLikerToOffer(@PathParam("Offer_ID") int OfferID) 
			throws JAXBException, IOException {
Offers offers = xmlAuslesen();
		
		java.util.ListIterator<Offer> iterator = offers.getOffer().listIterator();
		int index=0;
		while(iterator.hasNext()) {
			if(OfferID == iterator.next().getOfferID().intValue()) {
				index = iterator.nextIndex();
				offers.getOffer().get(iterator.nextIndex()).getLiker().getLink().add(Helper.SERVERROOT + "/offers/" + Helper.USERID);
			}
			iterator.next();
		}	
		xmlSchreiben(offers);
		return offers.getOffer().get(index).getLiker();
	}
	
	/*
	 * Füge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{OfferID}")
//	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("Offer_ID") int OfferID, @PathParam("Offer_ID") int CommentID) 
			throws JAXBException, IOException {
Offers offers = xmlAuslesen();
		
		java.util.ListIterator<Offer> iterator = offers.getOffer().listIterator();
		int offerindex=0;
		int commentindex=0;
		while(iterator.hasNext()) {
			if(OfferID == iterator.next().getOfferID().intValue()) {
				offerindex = iterator.nextIndex();
				java.util.ListIterator<Comment> iteratorComment = iterator.next().getComments().getComment().listIterator();
				while(iteratorComment.hasNext()) {
					if(CommentID == iteratorComment.next().getCommentID().intValue()) {
						commentindex = iteratorComment.nextIndex();
						offers.getOffer().get(offerindex).getComments().getComment().get(commentindex).getLiker().getLink().add(Helper.SERVERROOT + "/offers/" + Helper.USERID);
						xmlSchreiben(offers);
						return offers.getOffer().get(offerindex).getComments().getComment().get(commentindex);
					}
				}
			}
			iterator.next();
		}
		return null;
	}
	
	/*
	 * Füge Follower zu Offer hinzu.
	 *   ~> return-Wert: Offer inkl. neuem Follower <~
	 */
//	@POST
//	@Path ("/{OfferID}")
//	@Produces ( " application/xml" )
	public Offer addFollowerToOffer(@PathParam("Offer_ID") int OfferID) 
			throws JAXBException, IOException {
		Offers offers = xmlAuslesen();
		
		java.util.ListIterator<Offer> iterator = offers.getOffer().listIterator();
		int offerindex=0;
		while(iterator.hasNext()) {
			if(OfferID == iterator.next().getOfferID().intValue()) {
				offerindex = iterator.nextIndex();
				offers.getOffer().get(offerindex).getFollower().getLink().add(Helper.SERVERROOT + "/offers/" + Helper.USERID);
				xmlSchreiben(offers);
				return offers.getOffer().get(OfferID);
			}
			iterator.next();
		}
		return null;
	}
	
	/*
	 * Add Item zu Offer mit OfferID
	 *   ~> return-Wert: Offer inkl. neuem Item <~
	 */
//	@POST
//	@Path ("/{OfferID}")
//	@Produces ( " application/xml" )
	public Offer addFollowerToOffer(@PathParam("Offer_ID") int OfferID,
			@QueryParam("Item") String Item) 
			throws JAXBException, IOException {
		Offers offers = xmlAuslesen();
		
		java.util.ListIterator<Offer> iterator = offers.getOffer().listIterator();
		int offerindex=0;
		while(iterator.hasNext()) {
			if(OfferID == iterator.next().getOfferID().intValue()) {
				offerindex = iterator.nextIndex();
				offers.getOffer().get(offerindex).getItem().add(Item);
				xmlSchreiben(offers);
				return offers.getOffer().get(offerindex);
			}
			iterator.next();
		}
		return null;
	}
	
	
}