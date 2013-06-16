package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.ws.rs.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import stickerApp.*;

@Path( "/users" )
public class UserService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Users.xml";
	static final String SERVERROOT = "localhost:4434";
	static ObjectFactory ob=new ObjectFactory();
	static final int USERID = 0;
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Users, aus der XML Datei <~
	 */	
	private Users xmlAuslesen() throws JAXBException, FileNotFoundException {
		Users users=ob.createUsers();
		JAXBContext context = JAXBContext.newInstance(Users.class);
		Unmarshaller um = context.createUnmarshaller();
		users = (Users) um.unmarshal(new FileReader(XMLFILE));
		return users;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Users Users -> Users, die in die XML Datei geschrieben werden.)
	 */
	private void xmlSchreiben(Users Users) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Users.class);
		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		System.out.println("Ausgabe der gemarshalten Daten:");
		m.marshal(Users, System.out);
		
		Writer wr = null;
		try {
			wr = new FileWriter(XMLFILE);
			m.marshal(Users, wr);
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
	
	/*  Erstelle neuen User
	 */
	/*
	@PUT
	@Path ("/User/{UserID}")
	@Produces ("application/xml")
	public void createUser() throws JAXBException, IOException {
		Users users = xmlAuslesen();
	//Schleife durch alle User
		ListIterator<User> userIterator = users.getUser().listIterator();
		int userID=0;
		while(userIterator.hasNext()) {
			if(userID < users.getUser().get(userIterator.previousIndex()+1).getUserID().intValue()) {
				userID = users.getUser().get(userIterator.previousIndex()+1).getUserID().intValue();
			}
		}
		userID++;
		String userName = "User[" + userID + "]";
		User user = new User();
		user.setSelf(SERVERROOT + "/User/" + userID);
		user.setNickname(userName);
		user.setUserID(new BigInteger("" + userID));
		users.getUser().add(user);
		xmlSchreiben(users);
	}*/
	
	/* Zeigt alle User an.
	 * 		~> return-Wert: Alle User aus der XML Datei <~
	 */
	@GET
	@Produces ( " application/xml" )
	public Users allUsers() throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Users users=xmlAuslesen();
		
		return users;
	}
	
	/* Zeigt User mit userID an.
	 * 		~> return-Wert: User mit userID aus der XML Datei <~
	 */
	@GET
	@Path ("/{userID}")
	@Produces ( " application/xml" )
	public User oneUser(@PathParam("User_ID") int userID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Users users=xmlAuslesen();
		User user = users.getUser().get(userID);
		
		return user;
	}
	
	/* Lösche User mit UserID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public Users deleteUser(@PathParam("User_ID") int UserID) throws JAXBException, IOException {
		//Hole XML Daten
		Users users=xmlAuslesen();
		User user = users.getUser().remove(UserID);
		xmlSchreiben(users);
		return users;
	}
	
	/* Erstelle User mit UserID.
	 * 		~> return-Wert: User, die erstellt wurde <~
	 */
	@POST
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public User createUser(@PathParam("User_ID") int UserID, 
			String nickName
			) throws JAXBException, IOException {
		//Hole XML Daten
		Users users=xmlAuslesen();
		//neue User
		User user = new User();
		StickerContainer stickerContainer = new StickerContainer();
		CollectionContainer collectionContainer = new CollectionContainer();
		PhotoContainer photoContainer = new PhotoContainer();
		OfferContainer offerContainer = new OfferContainer();
		ShoppingCartContainer shoppingCartContainer = new ShoppingCartContainer();
		user.setStickerContainer(stickerContainer);
		user.setCollectionContainer(collectionContainer);
		user.setPhotoContainer(photoContainer);
		user.setOfferContainer(offerContainer);
		user.setShoppingCartContainer(shoppingCartContainer);
		Liker liker = new Liker();
		Follower follower = new Follower();
		Comments comments = new Comments();
		user.setLiker(liker);
		user.setFollower(follower);
		user.setComments(comments);
		int lastindex = users.getUser().lastIndexOf(user);
		lastindex++;
		BigInteger ID = new BigInteger("" + lastindex);
		user.setUserID(ID);
		user.setSelf(SERVERROOT + "/users/" + lastindex);
		user.setUserID(new BigInteger ("" + USERID));
		//zur Usersliste hinzufuegen
		users.getUser().add(lastindex, user);
		//ins XML zurückschreiben
		xmlSchreiben(users);
		return user;
	}
	
}