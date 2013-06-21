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
	@Path ("/{Collection_ID}")
	@Produces ( " application/xml" )
	public Collection oneCollection(@PathParam("Collection_ID") int collectionID) throws JAXBException, FileNotFoundException {
			//Hole XML Daten
			Collections collections=xmlAuslesen();
			java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
			while(iterator.hasNext()) {
				Collection collection = iterator.next();
				if(collectionID == collection.getCollectionID().intValue()) {
					return collection;
				}
			}
			return null;
	}
	
	/* Lösche Collection mit CollectionID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{Collection_ID}")
	@Produces ( " application/xml" )
	public Collections deleteCollection(@PathParam("Collection_ID") int collectionID) throws JAXBException, IOException {
		//Hole XML Daten
		Collections collections=xmlAuslesen();
		java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
		while(iterator.hasNext()) {
			int index = iterator.nextIndex();
			Collection collection = iterator.next();
			if(collectionID == collection.getCollectionID().intValue()) {
				collections.getCollection().remove(index);
			}
		}
		xmlSchreiben(collections);
		return collections;
	}
	
	
	/* Erstelle Collection mit CollectionID.
	 * 		~> return-Wert: Collection, die erstellt wurde <~
	 */
	@POST
	@QueryParam ("Title")
	@Produces ( " application/xml" )
	public Collection createCollection(/*@PathParam("Collection_ID") int CollectionID,*/ 
			@QueryParam("Title") String title) throws JAXBException, IOException {
		//Hole XML Daten
		Collections collections=xmlAuslesen();
		//neue Collection
		Collection collection = new Collection();
		
		int id = 0;
		for(Collection c : collections.getCollection()) {
			if(id <= c.getCollectionID().intValue()) {
				id = c.getCollectionID().intValue() + 1;
			}
		}
		collection.setCollectionID(new BigInteger(""+id));
		collection.setTitle(title);
		collection.setSelf(Helper.SERVERROOT + "/collections/" + id);
		collection.setUserID(new BigInteger ("" + Helper.USERID));
		collection.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		//zur Collectionsliste hinzufuegen
		collections.getCollection().add(collection);
		//ins XML zurückschreiben
		xmlSchreiben(collections);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		java.util.ListIterator<User> userIterator = users.getUser().listIterator();
		int userindex=0;
		Reference reference = new Reference();
		reference.setTitle(title);
		reference.setLink(Helper.SERVERROOT + "/collections/" + id);
		while(userIterator.hasNext()) {
			userindex = userIterator.nextIndex();
			User user = userIterator.next();
			if(Helper.USERID == user.getUserID().intValue()) {
				users.getUser().get(userindex).getCollectionContainer().getReference().add(reference);
				US.xmlSchreiben(users);
			}
		}
		
		return collection;
	}
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
//	@POST
//	@Path ("/{CollectionID}")
//	@Produces ( " application/xml" )
	public Comments addComment(@PathParam("Collection_ID") int CollectionID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Collections collections = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/collections/" + Helper.USERID);
		comment.setText(text);
		
		java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
		int commentID = 0;
		int collectionIndex=0;
		while(iterator.hasNext()) {
			collectionIndex=iterator.nextIndex();
			Collection collection = iterator.next();
			if(CollectionID == collection.getCollectionID().intValue()) {
				for(Comment c : collection.getComments().getComment()) {
					if (commentID <= c.getCommentID().intValue())
						commentID = c.getCommentID().intValue() + 1;
				}
				comment.setCommentID(new BigInteger("" + commentID));
				collections.getCollection().get(iterator.nextIndex()).getComments().getComment().add(comment);
				xmlSchreiben(collections);
			}
		}		
		return collections.getCollection().get(collectionIndex).getComments();
	}
	
	/*
	 * Füge Liker zu Collection hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{CollectionID}")
//	@Produces ( " application/xml" )
	public Liker addLikerToCollection(@PathParam("Collection_ID") int CollectionID) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		
		java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
		int collectionIndex=0;
		while(iterator.hasNext()) {
			collectionIndex = iterator.nextIndex();
			Collection collection = iterator.next();
			if(CollectionID == collection.getCollectionID().intValue()) {
				collections.getCollection().get(collectionIndex).getLiker().getLink().add(Helper.SERVERROOT + "/collections/" + Helper.USERID);
			}
		}	
		xmlSchreiben(collections);
		return collections.getCollection().get(collectionIndex).getLiker();
	}
	
	/*
	 * Füge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{CollectionID}")
//	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("Collection_ID") int CollectionID, @PathParam("Collection_ID") int CommentID) 
			throws JAXBException, IOException {
Collections collections = xmlAuslesen();
		
		java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
		int collectionindex=0;
		int commentindex=0;
		while(iterator.hasNext()) {
			collectionindex = iterator.nextIndex();
			Collection collection = iterator.next();
			if(CollectionID == collection.getCollectionID().intValue()) {
				java.util.ListIterator<Comment> iteratorComment = collection.getComments().getComment().listIterator();
				while(iteratorComment.hasNext()) {
					commentindex = iteratorComment.nextIndex();
					Comment comment = iteratorComment.next();
					if(CommentID == comment.getCommentID().intValue()) {
						collections.getCollection().get(collectionindex).getComments().getComment().get(commentindex).getLiker().getLink().add(Helper.SERVERROOT + "/collections/" + Helper.USERID);
						xmlSchreiben(collections);
						return collections.getCollection().get(collectionindex).getComments().getComment().get(commentindex);
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * Füge Follower zu Collection hinzu.
	 *   ~> return-Wert: Collection inkl. neuem Follower <~
	 */
//	@POST
//	@Path ("/{CollectionID}")
//	@Produces ( " application/xml" )
	public Collection addFollowerToCollection(@PathParam("Collection_ID") int CollectionID) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		
		java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
		int collectionindex=0;
		while(iterator.hasNext()) {
			collectionindex = iterator.nextIndex();
			Collection collection = iterator.next();
			if(CollectionID == collection.getCollectionID().intValue()) {
				collections.getCollection().get(collectionindex).getFollower().getLink().add(Helper.SERVERROOT + "/collections/" + Helper.USERID);
				xmlSchreiben(collections);
				return collections.getCollection().get(CollectionID);
			}
		}
		return null;
	}
	
	/*
	 * Update Title von Collection mit CollectionID
	 *   ~> return-Wert: Collection mit neuem Title <~
	 */
	/*@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )*/
	public Collection updateTitle(@PathParam("Collection_ID") int CollectionID,
			@QueryParam("Title") String title) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		
		java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
		int collectionindex=0;
		while(iterator.hasNext()) {
			collectionindex = iterator.nextIndex();
			Collection collection = iterator.next();
			if(CollectionID == collection.getCollectionID().intValue()) {
				collections.getCollection().get(collectionindex).setTitle(title);
				xmlSchreiben(collections);
				return collections.getCollection().get(collectionindex);
			}
		}
		return null;
	}
	
	/*
	 * Update Description von Collection mit CollectionID
	 *   ~> return-Wert: Collection mit neuer Description <~
	 */
	/*@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )*/
	public Collection updateDescription(@PathParam("Collection_ID") int CollectionID,
			@QueryParam("Description") String description) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		
		java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
		int collectionindex=0;
		while(iterator.hasNext()) {
			collectionindex = iterator.nextIndex();
			Collection collection = iterator.next();
			if(CollectionID == collection.getCollectionID().intValue()) {
				collections.getCollection().get(collectionindex).setDescription(description);
				xmlSchreiben(collections);
				return collections.getCollection().get(collectionindex);
			}
		}
		return null;
	}
	
	/*
	 * Add Item zu Collection mit CollectionID
	 *   ~> return-Wert: Collection inkl. neuem Item <~
	 */
	/*@POST
	@Path ("/{CollectionID}")
	@Produces ( " application/xml" )*/
	public Collection addItem(@PathParam("Collection_ID") int CollectionID,
			@QueryParam("Item") String Item) 
			throws JAXBException, IOException {
		Collections collections = xmlAuslesen();
		
		java.util.ListIterator<Collection> iterator = collections.getCollection().listIterator();
		int collectionindex=0;
		while(iterator.hasNext()) {
			collectionindex = iterator.nextIndex();
			Collection collection = iterator.next();
			if(CollectionID == collection.getCollectionID().intValue()) {
				collections.getCollection().get(collectionindex).getItem().add(Item);
				xmlSchreiben(collections);
				return collections.getCollection().get(collectionindex);
			}
		}
		return null;
	}
	
}