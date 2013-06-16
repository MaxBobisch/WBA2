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

import stickerApp.*;

@Path( "/collections" )
public class CollectionService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Collections.xml";
	static final String SERVERROOT = "localhost:4434";
	static ObjectFactory ob=new ObjectFactory();
	static final int USERID = 0;
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Collections, aus der XML Datei <~
	 */	
	private Collections xmlAuslesen() throws JAXBException, FileNotFoundException {
		Collections collections=ob.createCollections();
		JAXBContext context = JAXBContext.newInstance(Collections.class);
		Unmarshaller um = context.createUnmarshaller();
		collections = (Collections) um.unmarshal(new FileReader(XMLFILE));
		return collections;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Collections Collections -> Collections, die in die XML Datei geschrieben werden.)
	 */
	private void xmlSchreiben(Collections collections) throws JAXBException, IOException {
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
		collection.setSelf(SERVERROOT + "/collections/" + lastindex);
		collection.setUserID(new BigInteger ("" + USERID));
		collection.setOwner(SERVERROOT + "/users/" + USERID);
		//zur Collectionsliste hinzufuegen
		collections.getCollection().add(lastindex, collection);
		//ins XML zurückschreiben
		xmlSchreiben(collections);
		return collection;
	}
	
}