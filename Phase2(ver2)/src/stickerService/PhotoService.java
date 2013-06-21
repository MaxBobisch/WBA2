package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

import stickerApp.Comment;
import stickerApp.Comments;
import stickerApp.Follower;
import stickerApp.Liker;
import stickerApp.ObjectFactory;
import stickerApp.Photo;
import stickerApp.Photos;
import stickerApp.SinglePhoto;
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
	
	/* Erstelle Photo mit PhotoID.
	 * 		~> return-Wert: Photo, die erstellt wurde <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Photo createPhoto(@PathParam("Photo_ID") int PhotoID, 
			String title, 
			String description,
			String photoLink) throws JAXBException, IOException, DatatypeConfigurationException {
		//Hole XML Daten
		Photos photos=xmlAuslesen();
		//neue Photo
		Photo photo = new Photo();
		int id = 0;
		for(Photo c : photos.getPhoto()) {
			if(id <= c.getPhotoID().intValue()) {
				id = c.getPhotoID().intValue() + 1;
			}
		}
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
			if(Helper.USERID == userIterator.next().getUserID().intValue()) {
				userindex = userIterator.nextIndex();
				users.getUser().get(userindex).getPhotoContainer().getSinglePhoto().add(singlePhoto);
				US.xmlSchreiben(users);
			}
			userIterator.next();
		}
		
		return photo;
	}
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Comments addComment(@PathParam("Photo_ID") int PhotoID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Photos photos = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/photos/" + Helper.USERID);
		comment.setText(text);
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int commentID = 0;
		int index=0;
		while(iterator.hasNext()) {
			if(PhotoID == iterator.next().getPhotoID().intValue()) {
				for(Comment c : iterator.next().getComments().getComment()) {
					if (commentID <= c.getCommentID().intValue())
						commentID = c.getCommentID().intValue() + 1;
						index=iterator.nextIndex();
				}
				comment.setCommentID(new BigInteger("" + commentID));
				photos.getPhoto().get(iterator.nextIndex()).getComments().getComment().add(comment);
				xmlSchreiben(photos);
			}
			iterator.next();
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
			if(PhotoID == iterator.next().getPhotoID().intValue()) {
				index = iterator.nextIndex();
				photos.getPhoto().get(iterator.nextIndex()).getLiker().getLink().add(Helper.SERVERROOT + "/photos/" + Helper.USERID);
			}
			iterator.next();
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
			if(PhotoID == iterator.next().getPhotoID().intValue()) {
				photoindex = iterator.nextIndex();
				java.util.ListIterator<Comment> iteratorComment = iterator.next().getComments().getComment().listIterator();
				while(iteratorComment.hasNext()) {
					if(CommentID == iteratorComment.next().getCommentID().intValue()) {
						commentindex = iteratorComment.nextIndex();
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
			if(PhotoID == iterator.next().getPhotoID().intValue()) {
				photoindex = iterator.nextIndex();
				photos.getPhoto().get(photoindex).getFollower().getLink().add(Helper.SERVERROOT + "/photos/" + Helper.USERID);
				xmlSchreiben(photos);
				return photos.getPhoto().get(PhotoID);
			}
			iterator.next();
		}
		return null;
	}
	
	/*
	 * Füge Description zu Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuer Description <~
	 */
//	@POST
//	@Path ("/{PhotoID}")
//	@Produces ( " application/xml" )
	public Photo addDescriptionToPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Description") String description) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		
		java.util.ListIterator<Photo> iterator = photos.getPhoto().listIterator();
		int photoindex=0;
		while(iterator.hasNext()) {
			if(PhotoID == iterator.next().getPhotoID().intValue()) {
				photoindex = iterator.nextIndex();
				photos.getPhoto().get(photoindex).setDescription(description);
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
			iterator.next();
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
			if(PhotoID == iterator.next().getPhotoID().intValue()) {
				photoindex = iterator.nextIndex();
				photos.getPhoto().get(photoindex).getLocation().setLatitude(new BigDecimal(latitude));
				photos.getPhoto().get(photoindex).getLocation().setLongitude(new BigDecimal(longitude));
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
			iterator.next();
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
			if(PhotoID == iterator.next().getPhotoID().intValue()) {
				photoindex = iterator.nextIndex();
				photos.getPhoto().get(photoindex).setRelatedSticker(RelatedSticker);
				xmlSchreiben(photos);
				return photos.getPhoto().get(photoindex);
			}
			iterator.next();
		}
		return null;
	}
	
}