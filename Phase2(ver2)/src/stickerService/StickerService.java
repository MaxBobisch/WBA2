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

@Path( "/stickers" )
public class StickerService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Stickers.xml";
	static final String SERVERROOT = "localhost:4434";
	static ObjectFactory ob=new ObjectFactory();
	static final int USERID = 0;
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Stickers, aus der XML Datei <~
	 */	
	private Stickers xmlAuslesen() throws JAXBException, FileNotFoundException {
		Stickers stickers=ob.createStickers();
		JAXBContext context = JAXBContext.newInstance(Stickers.class);
		Unmarshaller um = context.createUnmarshaller();
		stickers = (Stickers) um.unmarshal(new FileReader(XMLFILE));
		return stickers;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Stickers Stickers -> Stickers, die in die XML Datei geschrieben werden.)
	 */
	private void xmlSchreiben(Stickers stickers) throws JAXBException, IOException {
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
		sticker.setSelf(SERVERROOT + "/stickers/" + lastindex);
		sticker.setUserID(new BigInteger ("" + USERID));
		sticker.setOwner(SERVERROOT + "/users/" + USERID);
		//zur Stickersliste hinzufuegen
		stickers.getSticker().add(lastindex, sticker);
		//ins XML zurückschreiben
		xmlSchreiben(stickers);
		return sticker;
	}
	
}