package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

import stickerApp.Adress;
import stickerApp.CollectionContainer;
import stickerApp.Comment;
import stickerApp.Comments;
import stickerApp.Follower;
import stickerApp.Liker;
import stickerApp.ObjectFactory;
import stickerApp.OfferContainer;
import stickerApp.PhotoContainer;
import stickerApp.ShoppingCartContainer;
import stickerApp.StickerContainer;
import stickerApp.User;
import stickerApp.Users;

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
		java.util.ListIterator<User> iterator = users.getUser().listIterator();
		while(iterator.hasNext()) {
			if(userID == iterator.next().getUserID().intValue()) {
				return iterator.next();
			}
			iterator.next();
		}
		return null;
	}
	
	/* L�sche User mit UserID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{UserID}")
	@Produces ( " application/xml" )
	public Users deleteUser(@PathParam("User_ID") int userID) throws JAXBException, IOException {
		//Hole XML Daten
		Users users=xmlAuslesen();
		java.util.ListIterator<User> iterator = users.getUser().listIterator();
		while(iterator.hasNext()) {
			if(userID == iterator.next().getUserID().intValue()) {
				users.getUser().remove(iterator.nextIndex());
			}
			iterator.next();
		}
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
		int id = 0;
		for(User c : users.getUser()) {
			if(id <= c.getUserID().intValue()) {
				id = c.getUserID().intValue() + 1;
			}
		}
		user.setUserID(new BigInteger(""+id));
		user.setSelf(Helper.SERVERROOT + "/users/" + id);
		//zur Usersliste hinzufuegen
		users.getUser().add(user);
		//ins XML zur�ckschreiben
		xmlSchreiben(users);
		return user;
	}
	
	/*
	 * F�ge Kommentar hinzu.
	 *   ~> return-Wert: Kommentare inkl. neuem Kommentar <~
	 */
//	@POST
//	@Path ("/{UserID}")
//	@Produces ( " application/xml" )
	public Comments addComment(@PathParam("User_ID") int UserID, 
			String text) throws JAXBException, IOException, DatatypeConfigurationException {
		Users users = xmlAuslesen();
		Comment comment = new Comment();
		comment.setDatetime(Helper.getXMLGregorianCalendarNow());
		comment.setUserID(new BigInteger(""+Helper.USERID));
		comment.setOwner(Helper.SERVERROOT + "/users/" + Helper.USERID);
		comment.setText(text);
		
		java.util.ListIterator<User> iterator = users.getUser().listIterator();
		int commentID = 0;
		int index=0;
		while(iterator.hasNext()) {
			if(UserID == iterator.next().getUserID().intValue()) {
				for(Comment c : iterator.next().getComments().getComment()) {
					if (commentID <= c.getCommentID().intValue())
						commentID = c.getCommentID().intValue() + 1;
						index=iterator.nextIndex();
				}
				comment.setCommentID(new BigInteger("" + commentID));
				users.getUser().get(iterator.nextIndex()).getComments().getComment().add(comment);
				xmlSchreiben(users);
			}
			iterator.next();
		}		
		return users.getUser().get(index).getComments();
	}
	
	/*
	 * F�ge Liker zu User hinzu.
	 *   ~> return-Wert: Liker inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{UserID}")
//	@Produces ( " application/xml" )
	public Liker addLikerToUser(@PathParam("User_ID") int UserID) 
			throws JAXBException, IOException {
		Users users = xmlAuslesen();
		
		java.util.ListIterator<User> iterator = users.getUser().listIterator();
		int index=0;
		while(iterator.hasNext()) {
			if(UserID == iterator.next().getUserID().intValue()) {
				index = iterator.nextIndex();
				users.getUser().get(iterator.nextIndex()).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
			}
			iterator.next();
		}	
		xmlSchreiben(users);
		return users.getUser().get(index).getLiker();
	}
	
	/*
	 * F�ge Liker zu Comment hinzu.
	 *   ~> return-Wert: Comment inkl. neuem Like <~
	 */
//	@POST
//	@Path ("/{UserID}")
//	@Produces ( " application/xml" )
	public Comment addLikerToComment(@PathParam("User_ID") int UserID, 
			@PathParam("User_ID") int CommentID) 
			throws JAXBException, IOException {
		Users users = xmlAuslesen();
		
		java.util.ListIterator<User> iterator = users.getUser().listIterator();
		int userindex=0;
		int commentindex=0;
		while(iterator.hasNext()) {
			if(UserID == iterator.next().getUserID().intValue()) {
				userindex = iterator.nextIndex();
				java.util.ListIterator<Comment> iteratorComment = iterator.next().getComments().getComment().listIterator();
				while(iteratorComment.hasNext()) {
					if(CommentID == iteratorComment.next().getCommentID().intValue()) {
						commentindex = iteratorComment.nextIndex();
						users.getUser().get(userindex).getComments().getComment().get(commentindex).getLiker().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
						xmlSchreiben(users);
						return users.getUser().get(userindex).getComments().getComment().get(commentindex);
					}
				}
			}
			iterator.next();
		}
		return null;
	}
	
	/*
	 * F�ge Follower zu User hinzu.
	 *   ~> return-Wert: User inkl. neuem Follower <~
	 */
//	@POST
//	@Path ("/{UserID}")
//	@Produces ( " application/xml" )
	public User addFollowerToUser(@PathParam("User_ID") int UserID) 
			throws JAXBException, IOException {
		Users users = xmlAuslesen();
		
		java.util.ListIterator<User> iterator = users.getUser().listIterator();
		int userindex=0;
		while(iterator.hasNext()) {
			if(UserID == iterator.next().getUserID().intValue()) {
				userindex = iterator.nextIndex();
				users.getUser().get(userindex).getFollower().getLink().add(Helper.SERVERROOT + "/users/" + Helper.USERID);
				xmlSchreiben(users);
				return users.getUser().get(UserID);
			}
			iterator.next();
		}
		return null;
	}
	
	/*
	 * F�ge HomeAdress zu User hinzu.
	 *   ~> return-Wert: HomeAdress <~
	 */
//	@POST
//	@Path ("/{UserID}")
//	@Produces ( " application/xml" )
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
		Adress adress = Helper.createAdress(FamilyName, FirstName, Street, Number, PostalCode, City, Province, Country, Telephone, Email);
		java.util.ListIterator<User> iterator = users.getUser().listIterator();
		int userindex=0;
		while(iterator.hasNext()) {
			if(UserID == iterator.next().getUserID().intValue()) {
				userindex = iterator.nextIndex();
				users.getUser().get(userindex).setHomeAdress(adress);				
				xmlSchreiben(users);
				return adress;
			}
			iterator.next();
		}
		return null;
	}
	
	/*
	 * F�ge ShopAdress zu User hinzu.
	 *   ~> return-Wert: ShopAdress <~
	 */
//	@POST
//	@Path ("/{UserID}")
//	@Produces ( " application/xml" )
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
		Adress adress = Helper.createAdress(FamilyName, FirstName, Street, Number, PostalCode, City, Province, Country, Telephone, Email);
		java.util.ListIterator<User> iterator = users.getUser().listIterator();
		int userindex=0;
		while(iterator.hasNext()) {
			if(UserID == iterator.next().getUserID().intValue()) {
				userindex = iterator.nextIndex();
				users.getUser().get(userindex).setShopAdress(adress);				
				xmlSchreiben(users);
				return adress;
			}
			iterator.next();
		}
		return null;
	}
	
}