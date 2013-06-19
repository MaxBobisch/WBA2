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

@Path( "/collections" )
public class CollectionService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Collections.xml";
	static ObjectFactory ob=new ObjectFactory();
	
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Collections, aus der XML Datei <~
	 */	
	protected Collections xmlAuslesen() throws JAXBException, FileNotFoundException {
		Collections collections=ob.createCollections();
		JAXBContext context = JAXBContext.newInstance(Collections.class);
		Unmarshaller um = context.createUnmarshaller();
		collections = (Collections) um.unmarshal(new FileReader(XMLFILE));
		return collections;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Collections Collections -> Collections, die in die XML Datei geschrieben werden.)
	 */
	protected void xmlSchreiben(Collections collections) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Collections.class);
		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		System.out.println("Ausgabe der gemarshalten Daten:");
		m.marshal(collections, System.out);
		
		Writer wr = null;
		try {
			wr = new FileWriter(XMLFILE);
			m.marshal(collections, wr);
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
	
		/* Zeigt alle Collection an.
	 * 		~> return-Wert: Alle Collection aus der XML Datei <~
	 */
	@GET
	@Produces ( " application/xml" )
	public Collections allCollections() throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Collections collections=xmlAuslesen();
		
		return collections;
	}
	
	/* Zeigt Collection mit CollectionID an.
	 * 		~> return-Wert: Collection mit CollectionID aus der XML Datei <~
	 */
	@GET
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Collection oneCollection(@PathParam("Collection_ID") int CollectionID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Collections collections=xmlAuslesen();
		Collection collection = collections.getCollection().get(CollectionID);
		
		return collection;
	}
	
	/* Lösche Collection mit CollectionID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Collections deleteCollection(@PathParam("Collection_ID") int CollectionID) throws JAXBException, IOException {
		//Hole XML Daten
		Collections collections=xmlAuslesen();
		Collection collection = collections.getCollection().remove(CollectionID);
		xmlSchreiben(collections);
		return collections;
	}
	
	
	/* Erstelle Collection mit CollectionID.
	 * 		~> return-Wert: Collection, die erstellt wurde <~
	 */
	@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Collection createCollection(@PathParam("Collection_ID") int CollectionID, 
			String title) throws JAXBException, IOException {
		//Hole XML Daten
		Collections collections=xmlAuslesen();
		//neue Collection
		Collection collection = new Collection();
		int lastindex = collections.getCollection().lastIndexOf(collection);
		lastindex++;
		BigInteger ID = new BigInteger("" + lastindex);
		collection.setCollectionID(ID);
		collection.setTitle(title);
		collection.setSelf(Helper.SERVERROOT + "/collections/" + lastindex);
		collection.setUserID(new BigInteger ("" + Helper.USERID));
		collection.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		//zur Collectionsliste hinzufuegen
		collections.getCollection().add(lastindex, collection);
		//ins XML zurückschreiben
		xmlSchreiben(collections);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		Reference reference = new Reference();
		reference.setTitle(title);
		reference.setLink(Helper.SERVERROOT + "/collections/" + CollectionID);
		users.getUser().get(Helper.USERID).getCollectionContainer().getReference().add(reference);
		US.xmlSchreiben(users);
		
		return collection;
	}
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
	@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Comments addComment(@PathParam("Collection_ID") int CollectionID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Collections collections = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		comment.setText(text);
		int lastindex = collections.getCollection().get(CollectionID).getComments().getComment().lastIndexOf(comment);
		lastindex++;
		comment.setCommentID(new BigInteger("" + lastindex));
		collections.getCollection().get(CollectionID).getComments().getComment().add(comment);
		xmlSchreiben(collections);
		return collections.getCollection().get(CollectionID).getComments();
	}
	
	/*
	 * Füge Liker zu Collection hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
	@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Liker addLikerToCollection(@PathParam("Collection_ID") int CollectionID) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		collections.getCollection().get(CollectionID).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(collections);
		return collections.getCollection().get(CollectionID).getLiker();
	}
	
	/*
	 * Füge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
	@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("Collection_ID") int CollectionID, @PathParam("Collection_ID") int CommentID) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		collections.getCollection().get(CollectionID).getComments().getComment().get(CommentID).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(collections);
		return collections.getCollection().get(CollectionID).getComments().getComment().get(CommentID);
	}
	
	/*
	 * Füge Follower zu Collection hinzu.
	 *   ~> return-Wert: Collections inkl. neuem Follower <~
	 */
	@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Collection addFollowerToCollection(@PathParam("Collection_ID") int CollectionID) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		collections.getCollection().get(CollectionID).getFollower().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(collections);
		return collections.getCollection().get(CollectionID);
	}
	
	/*
	 * Update Description von Collection mit CollectionID
	 *   ~> return-Wert: Collection mit neuer Description <~
	 */
	@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Collection updateDescription(@PathParam("Collection_ID") int CollectionID,
			@QueryParam("Description") String description) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		collections.getCollection().get(CollectionID).setDescription(description);
		xmlSchreiben(collections);
		return collections.getCollection().get(CollectionID);
	}
	
	/*
	 * Add Item zu Collection mit CollectionID
	 *   ~> return-Wert: Collection inkl. neuem Item <~
	 */
	@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )
	public Collection addItem(@PathParam("Collection_ID") int CollectionID,
			@QueryParam("Description") String Item) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		collections.getCollection().get(CollectionID).getItem().add(Item);
		xmlSchreiben(collections);
		return collections.getCollection().get(CollectionID);
	}
	
}