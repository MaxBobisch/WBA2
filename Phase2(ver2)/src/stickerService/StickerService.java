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
	
	public boolean contains (int id) throws FileNotFoundException, JAXBException {
		Stickers stickers = xmlAuslesen();
		java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
		while(iterator.hasNext()) {
			Sticker sticker = iterator.next();
			if(id == sticker.getStickerID().intValue()) {
				return true;
			}
		}
		return false;
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
	@Path ("/{sticker_ID}")
	@Produces ( " application/xml" )
	public Sticker oneSticker(@PathParam("Sticker_ID") int stickerID) throws JAXBException, FileNotFoundException {
			//Hole XML Daten
			Stickers stickers=xmlAuslesen();
			java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
			while(iterator.hasNext()) {
				Sticker sticker = iterator.next();
				if(stickerID == sticker.getStickerID().intValue()) {
					return sticker;
				}
			}
			return null;
	}
	
	/* Lösche Sticker mit StickerID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{Sticker_ID}")
	@Produces ( " application/xml" )
	public Stickers deleteSticker(@PathParam("Sticker_ID") int stickerID) throws JAXBException, IOException {
			//Hole XML Daten
			Stickers stickers=xmlAuslesen();
			java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
			while(iterator.hasNext()) {
				int index = iterator.nextIndex();
				Sticker sticker = iterator.next();
				if(stickerID == sticker.getStickerID().intValue()) {
					stickers.getSticker().remove(index);
				}
			}
			xmlSchreiben(stickers);
			return stickers;
	}
	
	public int nextIndex (Stickers stickers) {
		int id = 0;
		for(Sticker c : stickers.getSticker()) {
			if(id <= c.getStickerID().intValue()) {
				id = c.getStickerID().intValue() + 1;
			}
		}
		return id;
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
		int id = nextIndex(stickers);
		sticker.setStickerID(new BigInteger(""+id));
		if(!"".equals(title))sticker.setTitle(title);
		if(!"".equals(description))sticker.setDescription(description);
		Liker liker = new Liker();
		Follower follower = new Follower();
		Comments comments = new Comments();
		sticker.setLiker(liker);
		sticker.setFollower(follower);
		sticker.setComments(comments);
		sticker.setSelf(Helper.SERVERROOT + "/stickers/" + id);
		sticker.setUserID(new BigInteger ("" + Helper.USERID));
		sticker.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		//zur Stickersliste hinzufuegen
		stickers.getSticker().add(sticker);
		//ins XML zurückschreiben
		xmlSchreiben(stickers);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		java.util.ListIterator<User> userIterator = users.getUser().listIterator();
		int userindex=0;
		Reference reference = new Reference();
		reference.setTitle(title);
		reference.setLink(Helper.SERVERROOT + "/stickers/" + id);
		while(userIterator.hasNext()) {
			userindex = userIterator.nextIndex();
			User user = userIterator.next();
			if(Helper.USERID == user.getUserID().intValue()) {
				users.getUser().get(userindex).getStickerContainer().getReference().add(reference);
				US.xmlSchreiben(users);
			}
		}
		return sticker;
	}
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
//	@POST
//	@Path ("/{StickerID}")
//	@Produces ( " application/xml" )
	public Comments addCommentToSticker(@PathParam("Sticker_ID") int StickerID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Stickers stickers = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		comment.setText(text);
		
		java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
		int commentID = 0;
		int index=0;
		while(iterator.hasNext()) {
			index=iterator.nextIndex();
			Sticker sticker = iterator.next();
			if(StickerID == sticker.getStickerID().intValue()) {
				for(Comment c : sticker.getComments().getComment()) {
					if (commentID <= c.getCommentID().intValue())
						commentID = c.getCommentID().intValue() + 1;
				}
				comment.setCommentID(new BigInteger("" + commentID));
				stickers.getSticker().get(index).getComments().getComment().add(comment);
				xmlSchreiben(stickers);
			}
		}		
		return stickers.getSticker().get(index).getComments();
	}
	
	/*
	 * Füge Liker zu Sticker hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{StickerID}")
//	@Produces ( " application/xml" )
	public Liker addLikerToSticker(@PathParam("Sticker_ID") int StickerID) 
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		
		java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
		int index=0;
		while(iterator.hasNext()) {
			index=iterator.nextIndex();
			Sticker sticker = iterator.next();
			if(StickerID == sticker.getStickerID().intValue()) {
				stickers.getSticker().get(index).getLiker().getLink().add(Helper.SERVERROOT + "/stickers/" + Helper.USERID);
			}
		}	
		xmlSchreiben(stickers);
		return stickers.getSticker().get(index).getLiker();
	}
	
	/*
	 * Füge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{StickerID}")
//	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("Sticker_ID") int StickerID, @PathParam("Sticker_ID") int CommentID) 
			throws JAXBException, IOException {
Stickers stickers = xmlAuslesen();
		
		java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
		int stickerindex=0;
		int commentindex=0;
		while(iterator.hasNext()) {
			stickerindex=iterator.nextIndex();
			Sticker sticker = iterator.next();
			if(StickerID == sticker.getStickerID().intValue()) {
				java.util.ListIterator<Comment> iteratorComment = sticker.getComments().getComment().listIterator();
				while(iteratorComment.hasNext()) {
					commentindex = iteratorComment.nextIndex();
					Comment comment = iteratorComment.next();
					if(CommentID == comment.getCommentID().intValue()) {
						stickers.getSticker().get(stickerindex).getComments().getComment().get(commentindex).getLiker().getLink().add(Helper.SERVERROOT + "/stickers/" + Helper.USERID);
						xmlSchreiben(stickers);
						return stickers.getSticker().get(stickerindex).getComments().getComment().get(commentindex);
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * Füge Follower zu Sticker hinzu.
	 *   ~> return-Wert: Sticker inkl. neuem Follower <~
	 */
//	@POST
//	@Path ("/{StickerID}")
//	@Produces ( " application/xml" )
	public Sticker addFollowerToSticker(@PathParam("Sticker_ID") int StickerID) 
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		
		java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
		int stickerindex=0;
		while(iterator.hasNext()) {
			stickerindex=iterator.nextIndex();
			Sticker sticker = iterator.next();
			if(StickerID == sticker.getStickerID().intValue()) {
				stickers.getSticker().get(stickerindex).getFollower().getLink().add(Helper.SERVERROOT + "/stickers/" + Helper.USERID);
				xmlSchreiben(stickers);
				return stickers.getSticker().get(StickerID);
			}
		}
		return null;
	}
	
	/*
	 * Füge Title zu Sticker hinzu.
	 *   ~> return-Wert: Sticker inkl. neuem Title <~
	 */
//	@POST
//	@Path ("/{StickerID}")
//	@Produces ( " application/xml" )
	public Sticker updateTitleFromSticker(@PathParam("Sticker_ID") int StickerID,
			@QueryParam("Title") String title) 
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		
		java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
		int stickerindex=0;
		while(iterator.hasNext()) {
			stickerindex=iterator.nextIndex();
			Sticker sticker = iterator.next();
			if(StickerID == sticker.getStickerID().intValue()) {
				stickers.getSticker().get(stickerindex).setTitle(title);
				xmlSchreiben(stickers);
				return stickers.getSticker().get(stickerindex);
			}
		}
		return null;
	}
	
	/*
	 * Füge Description zu Sticker hinzu.
	 *   ~> return-Wert: Sticker inkl. neuer Description <~
	 */
//	@POST
//	@Path ("/{StickerID}")
//	@Produces ( " application/xml" )
	public Sticker updateDescriptionFromSticker(@PathParam("Sticker_ID") int StickerID,
			@QueryParam("Title") String description)  
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
		int stickerindex=0;
		while(iterator.hasNext()) {
			stickerindex=iterator.nextIndex();
			Sticker sticker = iterator.next();
			if(StickerID == sticker.getStickerID().intValue()) {
				stickerindex = iterator.nextIndex();
				stickers.getSticker().get(stickerindex).setDescription(description);
				xmlSchreiben(stickers);
				return stickers.getSticker().get(stickerindex);
			}
		}
		return null;
	}
	
	/*
	 * Füge Related Photo zu Sticker hinzu.
	 *   ~> return-Wert: Sticker inkl. neuem Related Photo <~
	 */
//	@POST
//	@Path ("/{StickerID}")
//	@Produces ( " application/xml" )
	public Sticker addRelatedPhotoToSticker(@PathParam("Sticker_ID") int StickerID,
			@QueryParam("RelatedPhoto") String RelatedPhoto)   
			throws JAXBException, IOException {
		Stickers stickers = xmlAuslesen();
		java.util.ListIterator<Sticker> iterator = stickers.getSticker().listIterator();
		int stickerindex=0;
		while(iterator.hasNext()) {
			stickerindex=iterator.nextIndex();
			Sticker sticker = iterator.next();
			if(StickerID == sticker.getStickerID().intValue()) {
				stickers.getSticker().get(stickerindex).getRelatedPhotos().getLink().add(RelatedPhoto);
				xmlSchreiben(stickers);
				return stickers.getSticker().get(stickerindex);
			}
		}
		return null;
	}
	
}