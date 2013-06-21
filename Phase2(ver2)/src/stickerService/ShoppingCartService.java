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

import stickerApp.Adress;
import stickerApp.ObjectFactory;
import stickerApp.Products;
import stickerApp.ShoppingCart;
import stickerApp.ShoppingCarts;
import stickerApp.SingleShoppingCart;
import stickerApp.User;
import stickerApp.Users;

@Path( "/shoppingcarts" )
public class ShoppingCartService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/ShoppingCarts.xml";
	static ObjectFactory ob=new ObjectFactory();
	static String[] STATE = new String[] {"created", "received", "accepted", "rejected", "cancelled", "fulfilled"};
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: ShoppingCarts, aus der XML Datei <~
	 */	
	protected ShoppingCarts xmlAuslesen() throws JAXBException, FileNotFoundException {
		ShoppingCarts shoppingCarts=ob.createShoppingCarts();
		JAXBContext context = JAXBContext.newInstance(ShoppingCarts.class);
		Unmarshaller um = context.createUnmarshaller();
		shoppingCarts = (ShoppingCarts) um.unmarshal(new FileReader(XMLFILE));
		return shoppingCarts;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(ShoppingCarts ShoppingCarts -> ShoppingCarts, die in die XML Datei geschrieben werden.)
	 */
	protected void xmlSchreiben(ShoppingCarts shoppingCarts) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(ShoppingCarts.class);
		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		System.out.println("Ausgabe der gemarshalten Daten:");
		m.marshal(shoppingCarts, System.out);
		
		Writer wr = null;
		try {
			wr = new FileWriter(XMLFILE);
			m.marshal(shoppingCarts, wr);
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
	
	
//	/* Zeigt alle ShoppingCart an.
//	 * 		~> return-Wert: Alle ShoppingCart aus der XML Datei <~
//	 */
//	@GET
//	@Produces ( " application/xml" )
//	public ShoppingCarts allShoppingCarts() throws JAXBException, FileNotFoundException {
//		//Hole XML Daten
//		ShoppingCarts shoppingCarts=xmlAuslesen();
//		
//		return shoppingCarts;
//	}
	
	public static boolean isEmpty( String s )
  {
     return s == null || s.trim().length() <= 0;
  }
	
	/* Zeigt alle ShoppingCart an mit State.
	 * 		~> return-Wert: Alle ShoppingCart mit State aus der XML Datei <~
	 */
	@SuppressWarnings("null")
	@GET
	@QueryParam ("State")
	@Produces ( " application/xml" )
	public ShoppingCarts createdShoppingCarts(@QueryParam("State") String State) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		if(!isEmpty(State)) {
			java.util.List<ShoppingCart> list = shoppingCarts.getShoppingCart();
			ShoppingCarts result = null;
			for(ShoppingCart shoppingCart: list) {
				for(String state : STATE)
					if(state.equals(shoppingCart.getState()))
						result.getShoppingCart().add(shoppingCart);
			}
			return result;
		}
		return shoppingCarts;
	}
	
	/* Zeigt ShoppingCart mit shoppingCartID an.
	 * 		~> return-Wert: ShoppingCart mit shoppingCartID aus der XML Datei <~
	 */
	@GET
	@Path ("/{ShoppingCartID}")
	@Produces ( " application/xml" )
	public ShoppingCart oneShoppingCart(@PathParam("ShoppingCartID") int shoppingCartID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			if(shoppingCartID == iterator.next().getShoppingCartID().intValue()) {
				return iterator.next();
			}
			iterator.next();
		}
		return null;
	}
	
	/* Lösche ShoppingCart mit ShoppingCartID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{ShoppingCartID}")
	@Produces ( " application/xml" )
	public ShoppingCarts deleteShoppingCart(@PathParam("ShoppingCartID") int shoppingCartID) throws JAXBException, IOException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			if(shoppingCartID == iterator.next().getShoppingCartID().intValue()) {
				shoppingCarts.getShoppingCart().remove(iterator.nextIndex());
			}
			iterator.next();
		}
		xmlSchreiben(shoppingCarts);
		return shoppingCarts;
	}			
	
	/* Erstelle ShoppingCart mit ShoppingCartID.
	 * 		~> return-Wert: ShoppingCart, die erstellt wurde <~
	 */
	@POST
	@Path ("/{ShoppingCartID}")
	@Produces ( " application/xml" )
	public ShoppingCart createShoppingCart(@PathParam("ShoppingCart_ID") int ShoppingCartID, 
			String title, 
			String description,
			String currency,
			Double price,
			String payment,
			String familyName,
			String firstName,
			String street,
			String number,
			String postalCode,
			String city,
			String province,
			String country,
			String telephone,
			String email) throws JAXBException, IOException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		//neues ShoppingCart
		ShoppingCart shoppingCart = new ShoppingCart();
		int id = 0;
		for(ShoppingCart c : shoppingCarts.getShoppingCart()) {
			if(id <= c.getShoppingCartID().intValue()) {
				id = c.getShoppingCartID().intValue() + 1;
			}
		}
		shoppingCart.setShoppingCartID(new BigInteger(""+id));
		shoppingCart.setProducts(new Products());
		Adress adress = Helper.createAdress(familyName,firstName,street,number,postalCode,city,province,country,telephone,email);
		shoppingCart.setShipTo(adress);
		shoppingCart.setPrice(new BigDecimal("" + price));
		shoppingCart.setPayment("n.a.");
		if("Bank Account".equals(payment)) shoppingCart.setPayment("Bank Account");
		if("Credit Card".equals(payment)) shoppingCart.setPayment("Credit Card");
		if("Paypal".equals(payment)) shoppingCart.setPayment("Paypal");
		shoppingCart.setState("created");
		shoppingCart.setCurrency(currency);
		shoppingCart.setSelf(Helper.SERVERROOT + "/shoppingCarts/" + id);
		shoppingCart.setShopID(new BigInteger ("" + Helper.SHOPID));
		shoppingCart.setShop(Helper.SERVERROOT + "/users/" + Helper.SHOPID);
		shoppingCart.setCustomerID(new BigInteger ("" + Helper.CUSTOMERID));
		shoppingCart.setCustomer(Helper.SERVERROOT + "/users/" + Helper.CUSTOMERID);
		//zur ShoppingCartsliste hinzufuegen
		shoppingCarts.getShoppingCart().add(shoppingCart);
		//ins XML zurückschreiben
		xmlSchreiben(shoppingCarts);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		SingleShoppingCart singleShoppingCart = new SingleShoppingCart();
		int anzahl = users.getUser().get(Helper.CUSTOMERID).getShoppingCartContainer().getSingleShoppingCart().get(ShoppingCartID).getCount().intValue();
		anzahl++;
		singleShoppingCart.setCount(new BigInteger(""+anzahl));
		singleShoppingCart.setCurrency(currency);
		singleShoppingCart.setLink(Helper.SERVERROOT + "/shoppingcarts/" + ShoppingCartID);
		users.getUser().get(Helper.USERID).getShoppingCartContainer().getSingleShoppingCart().add(singleShoppingCart);
		US.xmlSchreiben(users);
		
		return shoppingCart;
	}
	
	/*
	 * Change State
	 *   ~> return-Wert: ShoppingCart mit shoppingCartID<~
	 */
//	@POST
//	@Path ("/{ShoppingCartID}")
//	@Produces ( " application/xml" )
	public ShoppingCart changeState(@PathParam("ShoppingCartID") int ShoppingCartID,
			@QueryParam("Location") String State) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		
		int shoppingCartIndex = 0;
		for(String state:STATE) {
			if(State.equals(state)) {
				java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
				while(iterator.hasNext()) {
					if(ShoppingCartID == iterator.next().getShoppingCartID().intValue()) {
						shoppingCartIndex = iterator.next().getShoppingCartID().intValue();
						shoppingCarts.getShoppingCart().get(shoppingCartIndex).setState(State);
						xmlSchreiben(shoppingCarts);
						return shoppingCarts.getShoppingCart().get(ShoppingCartID);
					}
					iterator.next();
				}
			}
		}
		return null;
	}
	
	/*
	 * Add Item zu ShoppingCart
	 *   ~> return-Wert: ShoppingCart inkl. neuem Item <~
	 */
//	@POST
//	@Path ("/{ShoppingCartID}")
//	@Produces ( " application/xml" )
	public ShoppingCart addItemToShoppingCart(@PathParam("ShoppingCartID") int ShoppingCartID,
			@QueryParam("Location") String Item) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		
		int shoppingCartIndex = 0;
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			if(ShoppingCartID == iterator.next().getShoppingCartID().intValue()) {
				shoppingCartIndex = iterator.next().getShoppingCartID().intValue();
				shoppingCarts.getShoppingCart().get(shoppingCartIndex).getProducts().getItem().add(Item);
				xmlSchreiben(shoppingCarts);
				
				UserService US = new UserService();
				Users users = US.xmlAuslesen();
				java.util.ListIterator<User> userIterator = users.getUser().listIterator();
				int userindex=0;
				while(userIterator.hasNext()) {
					if(Helper.USERID == userIterator.next().getUserID().intValue()) {
						userindex = userIterator.nextIndex();
						users.getUser().get(userindex).getShoppingCartContainer().getSingleShoppingCart().get(shoppingCartIndex).getItem().add(Item);
						US.xmlSchreiben(users);
						
						return shoppingCarts.getShoppingCart().get(ShoppingCartID);
					}
					userIterator.next();
				}
			}
			iterator.next();
		}		
		return null;
	}
	
	
}