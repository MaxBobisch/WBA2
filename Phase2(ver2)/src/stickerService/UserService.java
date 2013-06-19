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
import javax.xml.datatype.DatatypeConfigurationException;

import stickerApp.*;

@Path( "/users" )
public class UserService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/Users.xml";
	static ObjectFactory ob=new ObjectFactory();
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Users, aus der XML Datei <~
	 */	
	protected Users xmlAuslesen() throws JAXBException, FileNotFoundException {
		Users users=ob.createUsers();
		JAXBContext context = JAXBContext.newInstance(Users.class);
		Unmarshaller um = context.createUnmarshaller();
		users = (Users) um.unmarshal(new FileReader(XMLFILE));
		return users;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Users Users -> Users, die in die XML Datei geschrieben werden.)
	 */
	protected void xmlSchreiben(Users Users) throws JAXBException, IOException {
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
		user.setSelf(Helper.SERVERROOT + "/users/" + lastindex);
		user.setUserID(new BigInteger ("" + Helper.USERID));
		//zur Usersliste hinzufuegen
		users.getUser().add(lastindex, user);
		//ins XML zurückschreiben
		xmlSchreiben(users);
		return user;
	}
	
	/*
	 * Füge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
	@POST
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public Comments addComment(@PathParam("User_ID") int UserID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Users users = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		comment.setText(text);
		int lastindex = users.getUser().get(UserID).getComments().getComment().lastIndexOf(comment);
		lastindex++;
		comment.setCommentID(new BigInteger("" + lastindex));
		users.getUser().get(UserID).getComments().getComment().add(comment);
		xmlSchreiben(users);
		return users.getUser().get(UserID).getComments();
	}
	
	/*
	 * Füge Liker zu User hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
	@POST
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public Liker addLikerToUser(@PathParam("User_ID") int UserID) 
			throws JAXBException, IOException {
		Users users = xmlAuslesen();
		users.getUser().get(UserID).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(users);
		return users.getUser().get(UserID).getLiker();
	}
	
	/*
	 * Füge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
	@POST
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("User_ID") int UserID, @PathParam("User_ID") int CommentID) 
			throws JAXBException, IOException {
		Users users = xmlAuslesen();
		users.getUser().get(UserID).getComments().getComment().get(CommentID).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(users);
		return users.getUser().get(UserID).getComments().getComment().get(CommentID);
	}
	
	/*
	 * Füge Follower zu User hinzu.
	 *   ~> return-Wert: User inkl. neuem Follower <~
	 */
	@POST
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public User addFollowerToUser(@PathParam("User_ID") int UserID) 
			throws JAXBException, IOException {
		Users users = xmlAuslesen();
		users.getUser().get(UserID).getFollower().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
		xmlSchreiben(users);
		return users.getUser().get(UserID);
	}
	
	/*
	 * Füge HomeAdress zu User hinzu.
	 *   ~> return-Wert: HomeAdress <~
	 */
	@POST
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public Adress addHomeAdressToUser(@PathParam("User_ID") int UserID,
			@QueryParam("FirstName") String FirstName,
			@QueryParam("FamilyName") String FamilyName,
			@QueryParam("Street") String Street,
			@QueryParam("Number") String Number,
			@QueryParam("City") String City,
			@QueryParam("Province") String Province,
			@QueryParam("Telephone") String Telephone,
			@QueryParam("PostalCode") String PostalCode,
			@QueryParam("Email") String Email,
			@QueryParam("Country") String Country)    
			throws JAXBException, IOException {
		Users users = xmlAuslesen();
		Adress adress = new Adress();
		adress.setFirstName(FirstName);
		adress.setFamilyName(FamilyName);
		adress.setStreet(Street);
		adress.setNumber(Number);
		adress.setPostalCode(PostalCode);
		adress.setCity(City);
		if(!"".equals(Province)) adress.setProvince(Province);
		adress.setCountry(Country);
		if(!"".equals(Telephone)) adress.setTelephone(new BigInteger(""+Telephone));
		if(!"".equals(Email)) adress.setEmail(Email);
		users.getUser().get(UserID).setHomeAdress(adress);
		xmlSchreiben(users);
		return adress;
	}
	
	/*
	 * Füge ShopAdress zu User hinzu.
	 *   ~> return-Wert: ShopAdress <~
	 */
	@POST
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public Adress addShopAdressToUser(@PathParam("User_ID") int UserID,
			@QueryParam("FirstName") String FirstName,
			@QueryParam("FamilyName") String FamilyName,
			@QueryParam("Street") String Street,
			@QueryParam("Number") String Number,
			@QueryParam("City") String City,
			@QueryParam("Province") String Province,
			@QueryParam("Telephone") String Telephone,
			@QueryParam("PostalCode") String PostalCode,
			@QueryParam("Email") String Email,
			@QueryParam("Country") String Country)    
			throws JAXBException, IOException {
		Users users = xmlAuslesen();
		Adress adress = new Adress();
		adress.setFirstName(FirstName);
		adress.setFamilyName(FamilyName);
		adress.setStreet(Street);
		adress.setNumber(Number);
		adress.setPostalCode(PostalCode);
		adress.setCity(City);
		if(!"".equals(Province)) adress.setProvince(Province);
		adress.setCountry(Country);
		if(!"".equals(Telephone)) adress.setTelephone(new BigInteger(""+Telephone));
		if(!"".equals(Email)) adress.setEmail(Email);
		users.getUser().get(UserID).setShopAdress(adress);
		xmlSchreiben(users);
		return adress;
	}
	
}