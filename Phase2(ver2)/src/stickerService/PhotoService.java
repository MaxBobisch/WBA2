package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
	static final String SERVERROOT = "localhost:4434";
	static ObjectFactory ob=new ObjectFactory();
	static final int USERID = 0;
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Photos, aus der XML Datei <~
	 */	
	private Photos xmlAuslesen() throws JAXBException, FileNotFoundException {
		Photos photos=ob.createPhotos();
		JAXBContext context = JAXBContext.newInstance(Photos.class);
		Unmarshaller um = context.createUnmarshaller();
		photos = (Photos) um.unmarshal(new FileReader(XMLFILE));
		return photos;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Photos Photos -> Photos, die in die XML Datei geschrieben werden.)
	 */
	private void xmlSchreiben(Photos photos) throws JAXBException, IOException {
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
	
	protected XMLGregorianCalendar getXMLGregorianCalendarNow() 
      throws DatatypeConfigurationException
{
  GregorianCalendar gregorianCalendar = new GregorianCalendar();
  DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
  XMLGregorianCalendar now = 
      datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
  return now;
}
	
	/* Erstelle Photo mit PhotoID.
	 * 		~> return-Wert: Photo, die erstellt wurde <~
	 */
	@POST
	@Path ("/{PhotoID}")
	@Produces ( " application/xml" )
	public Photo createPhoto(@PathParam("Photo_ID") int PhotoID, 
			String title, 
			String description) throws JAXBException, IOException, DatatypeConfigurationException {
		//Hole XML Daten
		Photos photos=xmlAuslesen();
		//neue Photo
		Photo photo = new Photo();
		int lastindex = photos.getPhoto().lastIndexOf(photo);
		lastindex++;
		BigInteger ID = new BigInteger("" + lastindex);
		photo.setPhotoID(ID);
		photo.setTitle(title);
		photo.setDescription(description);
		photo.setDatetime(getXMLGregorianCalendarNow());
		Liker liker = new Liker();
		Follower follower = new Follower();
		Comments comments = new Comments();
		photo.setLiker(liker);
		photo.setFollower(follower);
		photo.setComments(comments);
		photo.setSelf(SERVERROOT + "/photos/" + lastindex);
		photo.setUserID(new BigInteger ("" + USERID));
		photo.setOwner(SERVERROOT + "/users/" + USERID);
		//zur Photosliste hinzufuegen
		photos.getPhoto().add(lastindex, photo);
		//ins XML zurückschreiben
		xmlSchreiben(photos);
		return photo;
	}
	
}