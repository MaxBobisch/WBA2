package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import stickerApp.*;

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
	@Path ("/{photoID}")
	@Produces ( " application/xml" )
	public Photo onePhoto(@PathParam("Photo_ID") int photoID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Photos photos=xmlAuslesen();
		Photo photo = photos.getPhoto().get(photoID);
		
		return photo;
	}
	
	/* Lösche Photo mit PhotoID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Photos deletePhoto(@PathParam("Photo_ID") int PhotoID) throws JAXBException, IOException {
		//Hole XML Daten
		Photos photos=xmlAuslesen();
		Photo photo = photos.getPhoto().remove(PhotoID);
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
		int lastindex = photos.getPhoto().lastIndexOf(photo);
		lastindex++;
		BigInteger ID = new BigInteger("" + lastindex);
		photo.setPhotoID(ID);
		if(!"".equals(title))photo.setTitle(title);
		photo.setDescription(description);
		photo.setDatetime(Helper.getXMLGregorianCalendarNow());
		Liker liker = new Liker();
		Follower follower = new Follower();
		Comments comments = new Comments();
		photo.setLiker(liker);
		photo.setFollower(follower);
		photo.setComments(comments);
		photo.setSelf(Helper.SERVERROOT + "/photos/" + lastindex);
		photo.setUserID(new BigInteger ("" + Helper.USERID));
		photo.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		photo.setPhotoLink(photoLink);
		//zur Photosliste hinzufuegen
		photos.getPhoto().add(lastindex, photo);
		//ins XML zurückschreiben
		xmlSchreiben(photos);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		SinglePhoto singlePhoto = new SinglePhoto();
		singlePhoto.setPhotoLink(photoLink);
		if(!"".equals(title))singlePhoto.setTitle(title);
		singlePhoto.setLink(Helper.SERVERROOT + "/offers/" + PhotoID);
		users.getUser().get(Helper.USERID).getPhotoContainer().getSinglePhoto().add(singlePhoto);
		US.xmlSchreiben(users);
		
		return photo;
	}
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Comments addComment(@PathParam("Photo_ID") int PhotoID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Photos photos = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		comment.setText(text);
		int lastindex = photos.getPhoto().get(PhotoID).getComments().getComment().lastIndexOf(comment);
		lastindex++;
		comment.setCommentID(new BigInteger("" + lastindex));
		photos.getPhoto().get(PhotoID).getComments().getComment().add(comment);
		xmlSchreiben(photos);
		return photos.getPhoto().get(PhotoID).getComments();
	}
	
	/*
	 * Füge Liker zu Photo hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Liker addLikerToPhoto(@PathParam("Photo_ID") int PhotoID) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		photos.getPhoto().get(PhotoID).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(photos);
		return photos.getPhoto().get(PhotoID).getLiker();
	}
	
	/*
	 * Füge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("Photo_ID") int PhotoID, @PathParam("Photo_ID") int CommentID) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		photos.getPhoto().get(PhotoID).getComments().getComment().get(CommentID).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(photos);
		return photos.getPhoto().get(PhotoID).getComments().getComment().get(CommentID);
	}
	
	/*
	 * Füge Follower zu Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuem Follower <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Photo addFollowerToPhoto(@PathParam("Photo_ID") int PhotoID) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		photos.getPhoto().get(PhotoID).getFollower().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(photos);
		return photos.getPhoto().get(PhotoID);
	}
	
	/*
	 * Füge Description zu Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuer Description <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Photo addDescriptionToPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Description") String description) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		photos.getPhoto().get(PhotoID).setDescription(description);
		xmlSchreiben(photos);
		return photos.getPhoto().get(PhotoID);
	}
	
	/*
	 * Füge Location zu Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuer Location <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Photo addLocationToPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Latitude") double latitude,
			@QueryParam("Longitude") double longitude) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		photos.getPhoto().get(PhotoID).getLocation().setLatitude(new BigDecimal(latitude));
		photos.getPhoto().get(PhotoID).getLocation().setLongitude(new BigDecimal(longitude));
		xmlSchreiben(photos);
		return photos.getPhoto().get(PhotoID);
	}
	
	/*
	 * Füge Related Sticker zu Photo hinzu.
	 *   ~> return-Wert: Photo inkl. neuem Related Sticker <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Photo addFollowerToPhoto(@PathParam("Photo_ID") int PhotoID,
			@QueryParam("Location") String RelatedSticker) 
			throws JAXBException, IOException {
		Photos photos = xmlAuslesen();
		photos.getPhoto().get(PhotoID).setRelatedSticker(RelatedSticker);
		xmlSchreiben(photos);
		return photos.getPhoto().get(PhotoID);
	}
	
}