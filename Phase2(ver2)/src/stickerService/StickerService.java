package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;

import javax.ws.rs.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import stickerApp.*;

@Path( "/stickers" )
public class StickerService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Stickers.xml";
	static ObjectFactory ob=new ObjectFactory();
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Stickers, aus der XML Datei <~
	 */	
	protected Stickers xmlAuslesen() throws JAXBException, FileNotFoundException {
		Stickers stickers=ob.createStickers();
		JAXBContext context = JAXBContext.newInstance(Stickers.class);
		Unmarshaller um = context.createUnmarshaller();
		stickers = (Stickers) um.unmarshal(new FileReader(XMLFILE));
		return stickers;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Stickers Stickers -> Stickers, die in die XML Datei geschrieben werden.)
	 */
	protected void xmlSchreiben(Stickers stickers) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Stickers.class);
		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		System.out.println("Ausgabe der gemarshalten Daten:");
		m.marshal(stickers, System.out);
		
		Writer wr = null;
		try {
			wr = new FileWriter(XMLFILE);
			m.marshal(stickers, wr);
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
	
	
	/* Zeigt alle Sticker an.
	 * 		~> return-Wert: Alle Sticker aus der XML Datei <~
	 */
	@GET
	@Produces ( " application/xml" )
	public Stickers allStickers() throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Stickers stickers=xmlAuslesen();
		
		return stickers;
	}
	
	/* Zeigt Sticker mit stickerID an.
	 * 		~> return-Wert: Sticker mit stickerID aus der XML Datei <~
	 */
	@GET
	@Path ("/{stickerID}")
	@Produces ( " application/xml" )
	public Sticker oneSticker(@PathParam("Sticker_ID") int stickerID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Stickers stickers=xmlAuslesen();
		Sticker sticker = stickers.getSticker().get(stickerID);
		
		return sticker;
	}
	
	/* Lösche Sticker mit StickerID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Stickers deleteSticker(@PathParam("Sticker_ID") int StickerID) throws JAXBException, IOException {
		//Hole XML Daten
		Stickers stickers=xmlAuslesen();
		Sticker sticker = stickers.getSticker().remove(StickerID);
		xmlSchreiben(stickers);
		return stickers;
	}
	
	/* Erstelle Sticker mit StickerID.
	 * 		~> return-Wert: Sticker, die erstellt wurde <~
	 */
	@POST
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Sticker createSticker(@PathParam("Sticker_ID") int StickerID, 
			String title, 
			String description) throws JAXBException, IOException {
		//Hole XML Daten
		Stickers stickers=xmlAuslesen();
		//neue Sticker
		Sticker sticker = new Sticker();
		int lastindex = stickers.getSticker().lastIndexOf(sticker);
		lastindex++;
		BigInteger ID = new BigInteger("" + lastindex);
		sticker.setStickerID(ID);
		sticker.setTitle(title);
		sticker.setDescription(description);
		Liker liker = new Liker();
		Follower follower = new Follower();
		Comments comments = new Comments();
		sticker.setLiker(liker);
		sticker.setFollower(follower);
		sticker.setComments(comments);
		sticker.setSelf(Helper.SERVERROOT + "/stickers/" + lastindex);
		sticker.setUserID(new BigInteger ("" + Helper.USERID));
		sticker.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		//zur Stickersliste hinzufuegen
		stickers.getSticker().add(lastindex, sticker);
		//ins XML zurückschreiben
		xmlSchreiben(stickers);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		Reference reference = new Reference();
		reference.setTitle(title);
		reference.setLink(Helper.SERVERROOT + "/stickers/" + StickerID);
		users.getUser().get(Helper.USERID).getStickerContainer().getReference().add(reference);
		US.xmlSchreiben(users);
		
		return sticker;
	}
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
	@POST
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Comments addComment(@PathParam("Sticker_ID") int StickerID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Stickers stickers = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		comment.setText(text);
		int lastindex = stickers.getSticker().get(StickerID).getComments().getComment().lastIndexOf(comment);
		lastindex++;
		comment.setCommentID(new BigInteger("" + lastindex));
		stickers.getSticker().get(StickerID).getComments().getComment().add(comment);
		xmlSchreiben(stickers);
		return stickers.getSticker().get(StickerID).getComments();
	}
	
	/*
	 * Füge Liker zu Sticker hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
	@POST
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Liker addLikerToSticker(@PathParam("Sticker_ID") int StickerID) 
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		stickers.getSticker().get(StickerID).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(stickers);
		return stickers.getSticker().get(StickerID).getLiker();
	}
	
	/*
	 * Füge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
	@POST
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("Sticker_ID") int StickerID, @PathParam("Sticker_ID") int CommentID) 
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		stickers.getSticker().get(StickerID).getComments().getComment().get(CommentID).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(stickers);
		return stickers.getSticker().get(StickerID).getComments().getComment().get(CommentID);
	}
	
	/*
	 * Füge Follower zu Sticker hinzu.
	 *   ~> return-Wert: Sticker inkl. neuem Follower <~
	 */
	@POST
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Sticker addFollowerToSticker(@PathParam("Sticker_ID") int StickerID) 
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		stickers.getSticker().get(StickerID).getFollower().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(stickers);
		return stickers.getSticker().get(StickerID);
	}
	
	/*
	 * Füge Title zu Sticker hinzu.
	 *   ~> return-Wert: Sticker inkl. neuem Title <~
	 */
	@POST
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Sticker addTitleToSticker(@PathParam("Sticker_ID") int StickerID,
			@QueryParam("Title") String title) 
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		stickers.getSticker().get(StickerID).setTitle(title);
		xmlSchreiben(stickers);
		return stickers.getSticker().get(StickerID);
	}
	
	/*
	 * Füge Description zu Sticker hinzu.
	 *   ~> return-Wert: Sticker inkl. neuer Description <~
	 */
	@POST
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Sticker addDescriptionToSticker(@PathParam("Sticker_ID") int StickerID,
			@QueryParam("Title") String description)  
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		stickers.getSticker().get(StickerID).setDescription(description);
		xmlSchreiben(stickers);
		return stickers.getSticker().get(StickerID);
	}
	
	/*
	 * Füge Related Photo zu Sticker hinzu.
	 *   ~> return-Wert: Sticker inkl. neuem Related Photo <~
	 */
	@POST
	@Path ("/{StickerID}")
	@Produces ( " application/xml" )
	public Sticker addRelatedPhotoToSticker(@PathParam("Sticker_ID") int StickerID,
			@QueryParam("RelatedPhoto") String RelatedPhoto)   
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		stickers.getSticker().get(StickerID).getRelatedPhotos().getLink().add(RelatedPhoto);
		xmlSchreiben(stickers);
		return stickers.getSticker().get(StickerID);
	}
	
}