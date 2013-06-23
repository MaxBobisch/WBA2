package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;

import PubSub.PubSubHandler;

import stickerApp.Comment;
import stickerApp.Comments;
import stickerApp.Follower;
import stickerApp.Liker;
import stickerApp.ObjectFactory;
import stickerApp.Photo;
import stickerApp.Photos;
import stickerApp.SinglePhoto;
import stickerApp.Sticker;
import stickerApp.Stickers;
import stickerApp.User;
import stickerApp.Users;

@Path( "/photos" )
public class PhotoService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Photos.xml";
	static ObjectFactory ob=new ObjectFactory();
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Photos, aus der XML Datei <~
	 */	
	protected Photos xmlAuslesen() throws JAXBException, FileNotFoundException {
		Photos photos=ob.createPhotos();
		JAXBContext context = JAXBContext.newInstance(Photos.class);
		Unmarshaller um = context.createUnmarshaller();
		photos = (Photos) um.unmarshal(new FileReader(XMLFILE));
		return photos;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Photos Photos -> Photos, die in die XML Datei geschrieben werden.)
	 */
	protected void xmlSchreiben(Photos photos) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Photos.class);
		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		System.out.println("Ausgabe der gemarshalten Daten:");
		m.marshal(photos, System.out);
		
		Writer wr = null;
		try {
			wr = new FileWriter(XMLFILE);
			m.marshal(photos, wr);
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
		Photos photos = xmlAuslesen();
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		while(iterator.hasNext()) {
			Photo photo = iterator.next();
			if(id == photo.getPhotoID().intValue()) {
				return true;
			}
		}
		return false;
	}
	
	/* Zeigt alle Photo an.
	 * 		~> return-Wert: Alle Photo aus der XML Datei <~
	 */
	@GET
	@Produces ( " application/xml" )
	public Photos allPhotos() throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Photos photos=xmlAuslesen();
		
		return photos;
	}
	
	/* Zeigt Photo mit photoID an.
	 * 		~> return-Wert: Photo mit photoID aus der XML Datei <~
	 */
	@GET
	@Path ("/{Photo_ID}")
	@Produces ( " application/xml" )
	public Photo onePhoto(@PathParam("Photo_ID") int photoID) throws JAXBException, FileNotFoundException {
			//Hole XML Daten
			Photos photos=xmlAuslesen();
			java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
			while(iterator.hasNext()) {
				Photo photo = iterator.next();
				if(photoID == photo.getPhotoID().intValue()) {
					return photo;
				}
			}
			return null;
	}
	
	/* Lösche Photo mit PhotoID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{Photo_ID}")
	@Produces ( " application/xml" )
	public Photos deletePhoto(@PathParam("Photo_ID") int photoID) throws JAXBException, IOException {
			//Hole XML Daten
			Photos photos=xmlAuslesen();
			java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
			while(iterator.hasNext()) {
				int index = iterator.nextIndex();
				Photo photo = iterator.next();
				if(photoID == photo.getPhotoID().intValue()) {
					photos.getPhoto().remove(index);
				}
			}
			xmlSchreiben(photos);
			return photos;
	}
	
	public int nextIndex (Photos photos) {
		int id = 0;
		for(Photo c : photos.getPhoto()) {
			if(id <= c.getPhotoID().intValue()) {
				id = c.getPhotoID().intValue() + 1;
			}
		}
		return id;
	}
	
	/* Erstelle Photo mit PhotoID.
	 * 		~> return-Wert: Photo, die erstellt wurde <~
	 */
	@POST
	/*@Path ("/{PhotoID}")*/
	@Produces ( " application/xml" )
	public Photo createPhoto(/*@PathParam("Photo_ID") int PhotoID,*/ 
			String title, 
			String description,
			String photoLink) throws JAXBException, IOException, DatatypeConfigurationException, XMPPException {
		//Hole XML Daten
		Photos photos=xmlAuslesen();
		//neue Photo
		Photo photo = new Photo();
		int id = nextIndex(photos);
		photo.setPhotoID(new BigInteger(""+id));
		if(!"".equals(title))photo.setTitle(title);
		photo.setDescription(description);
		photo.setDatetime(Helper.getXMLGregorianCalendarNow());
		Liker liker = new Liker();
		Follower follower = new Follower();
		Comments comments = new Comments();
		photo.setLiker(liker);
		photo.setFollower(follower);
		photo.setComments(comments);
		photo.setSelf(Helper.SERVERROOT + "/photos/" + id);
		photo.setUserID(new BigInteger ("" + Helper.USERID));
		photo.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		photo.setPhotoLink(photoLink);
		//zur Photosliste hinzufuegen
		photos.getPhoto().add(photo);
		//ins XML zurückschreiben
		xmlSchreiben(photos);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		java.util.ListIterator<User> userIterator = users.getUser().listIterator();
		int userindex=0;
		SinglePhoto singlePhoto = new SinglePhoto();
		singlePhoto.setPhotoLink(photoLink);
		if(!"".equals(title))singlePhoto.setTitle(title);
		singlePhoto.setLink(Helper.SERVERROOT + "/photos/" + id);
		while(userIterator.hasNext()) {
			userindex = userIterator.nextIndex();
			User user = userIterator.next();
			if(Helper.USERID == user.getUserID().intValue()) {
				users.getUser().get(userindex).getPhotoContainer().getSinglePhoto().add(singlePhoto);
				US.xmlSchreiben(users);
			}
		}
		
	//XMPP funktionaliteat
			String nodeID = "photo" + id;
//			LeafNode photoLeaf = PubSubHandler.createNode(nodeID);
//			Node photoNode = PubSubHandler.publishToNodePayload("photos", nodeID, null, "PHOTO CREATED:\n" + xmlToString(photo));
//			PubSubHandler.subscribeNode("user" + Helper.USERID, nodeID);
//			System.out.println(photoNode.getSubscriptions().toString());
		
		return photo;
	}
	
  public String xmlToString (Photo photo) throws JAXBException, IOException {
		ObjectFactory ob=new ObjectFactory();
		JAXBContext context = JAXBContext.newInstance(Photo.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter wr = null;
		try {
			wr = new StringWriter();
			m.marshal(photo, wr);
			return wr.toString();
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
  
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Comments addCommentToPhoto(@PathParam("Photo_ID") int PhotoID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Photos photos = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		comment.setText(text);
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int commentID = 0;
		int index=0;
		while(iterator.hasNext()) {
			index = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID ==photo.getPhotoID().intValue()) {
				for(Comment c : photo.getComments().getComment()) {
					if (commentID <= c.getCommentID().intValue())
						commentID = c.getCommentID().intValue() + 1;
				}
				comment.setCommentID(new BigInteger("" + commentID));
				photos.getPhoto().get(iterator.nextIndex()).getComments().getComment().add(comment);
				xmlSchreiben(photos);
			}
		}		
		return photos.getPhoto().get(index).getComments();
	}
	
	/*
	 * Füge Liker zu Photo hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Liker addLikerToPhoto(@PathParam("Photo_ID") int PhotoID) 
			throws JAXBException, IOException {
Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int index=0;
		while(iterator.hasNext()) {
			index = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				photos.getPhoto().get(index).getLiker().getLink().add(Helper.SERVERROOT + "/photos/" + Helper.USERID);
			}
		}	
		xmlSchreiben(photos);
		return photos.getPhoto().get(index).getLiker();
	}
	
	/*
	 * Füge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("Photo_ID") int PhotoID, @PathParam("Photo_ID") int CommentID) 
			throws JAXBException, IOException {
Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		int commentindex=0;
		while(iterator.hasNext()) {
			photoindex = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				java.util.ListIterator<Comment> iteratorComment = photo.getComments().getComment().listIterator();
				while(iteratorComment.hasNext()) {
					commentindex = iteratorComment.nextIndex();
					Comment comment = iteratorComment.next();
					if(CommentID == comment.getCommentID().intValue()) {
						photos.getPhoto().get(photoindex).getComments().getComment().get(commentindex).getLiker().getLink().add(Helper.SERVERROOT + "/photos/" + Helper.USERID);
						xmlSchreiben(photos);
						return photos.getPhoto().get(photoindex).getComments().getComment().get(commentindex);
					}
				}
			}
			iterator.next();
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
	public Photo addFollowerToPhoto(@PathParam("Photo_ID") int PhotoID) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		while(iterator.hasNext()) {
			photoindex = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				photos.getPhoto().get(photoindex).getFollower().getLink().add(Helper.SERVERROOT + "/photos/" + Helper.USERID);
				xmlSchreiben(photos);
				return photos.getPhoto().get(PhotoID);
			}
		}
		return null;
	}
	
	/*
	 * Update Title von Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuem Titel <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Photo updateTitleFromPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Title") String title) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		while(iterator.hasNext()) {
			photoindex = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				photos.getPhoto().get(photoindex).setTitle(title);
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
		}
		return null;
	}
	
	/*
	 * Update Photolink von Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuem Photolink <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Photo updatePhotolinkFromPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Title") String photoLink) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		while(iterator.hasNext()) {
			photoindex = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				photos.getPhoto().get(photoindex).setPhotoLink(photoLink);
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
		}
		return null;
	}
	
	/*
	 * Update Description zu Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuer Description <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Photo updateDescriptionFromPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Description") String description) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		while(iterator.hasNext()) {
			photoindex = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				photos.getPhoto().get(photoindex).setDescription(description);
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
		}
		return null;
	}
	
	/*
	 * Update Related Sticker von Photo.
	 *   ~> return-Wert: Photo inkl. neuem Related Sticker <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Photo updateRelatedStickerFromPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Description") String relatedSticker) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		while(iterator.hasNext()) {
			photoindex = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				photos.getPhoto().get(photoindex).setRelatedSticker(relatedSticker);
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
		}
		return null;
	}
	
	/*
	 * Füge Location zu Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuer Location <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Photo addLocationToPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Latitude") double latitude,
			@QueryParam("Longitude") double longitude) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		while(iterator.hasNext()) {
			photoindex = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				photos.getPhoto().get(photoindex).getLocation().setLatitude(new BigDecimal(latitude));
				photos.getPhoto().get(photoindex).getLocation().setLongitude(new BigDecimal(longitude));
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
		}
		return null;
	}
	
	/*
	 * Füge Related Sticker zu Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuem Related Sticker <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Photo addFollowerToPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Location") String RelatedSticker) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		while(iterator.hasNext()) {
			photoindex = iterator.nextIndex();
			Photo photo = iterator.next();
			if(PhotoID == photo.getPhotoID().intValue()) {
				photos.getPhoto().get(photoindex).setRelatedSticker(RelatedSticker);
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
		}
		return null;
	}
	
}