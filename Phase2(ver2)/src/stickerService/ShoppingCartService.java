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
import stickerApp.SinglePhoto;
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
	
	/* Zeigt alle ShoppingCart an mit State.
	 * 		~> return-Wert: Alle ShoppingCart mit State aus der XML Datei <~
	 */
	@GET
	@QueryParam ("state")
	@Produces ( " application/xml" )
	public ShoppingCarts allShoppingCarts(@QueryParam("state") String QueryState) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		ShoppingCarts result = null;		
		for(String validState : STATE) {
			if(validState.equals(QueryState)) {
				result = new ShoppingCarts();
				while(iterator.hasNext()) {
					ShoppingCart shoppingCart = iterator.next();
					if(validState.equals(shoppingCart.getState())) {
						result.getShoppingCart().add(shoppingCart);
					}
				}
			}
		}
		return result!=null ? result : shoppingCarts ;
	}
	
	/* Zeigt ShoppingCart mit shoppingCartID an.
	 * 		~> return-Wert: ShoppingCart mit shoppingCartID aus der XML Datei <~
	 */
	@GET
	@Path ("/{ShoppingCart_ID}")
	@Produces ( " application/xml" )
	public ShoppingCart oneShoppingCart(@PathParam("ShoppingCart_ID") int shoppingCartID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			ShoppingCart shoppingCart = iterator.next();
			if(shoppingCartID == shoppingCart.getShoppingCartID().intValue()) {
				return shoppingCart;
			}
		}
		return null;
	}
	
	/* Lösche ShoppingCart mit ShoppingCartID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{ShoppingCart_ID}")
	@Produces ( " application/xml" )
	public ShoppingCarts deleteShoppingCart(@PathParam("ShoppingCart_ID") int shoppingCartID) throws JAXBException, IOException {
			//Hole XML Daten
			ShoppingCarts shoppingCarts=xmlAuslesen();
			java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
			while(iterator.hasNext()) {
				int index = iterator.nextIndex();
				ShoppingCart shoppingCart = iterator.next();
				if(shoppingCartID == shoppingCart.getShoppingCartID().intValue()) {
					shoppingCarts.getShoppingCart().remove(index);
				}
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
		if("Bank Account".equals(payment)) shoppingCart.setPayment("Bank Account");
		else if("Credit Card".equals(payment)) shoppingCart.setPayment("Credit Card");
		else if("Paypal".equals(payment)) shoppingCart.setPayment("Paypal");
		else shoppingCart.setPayment("n.a.");
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
		java.util.ListIterator<User> userIterator = users.getUser().listIterator();
		SingleShoppingCart singleShoppingCart = new SingleShoppingCart();
		int userindex=0;
		while(userIterator.hasNext()) {
			userindex = userIterator.nextIndex();
			User user = userIterator.next();
			if(Helper.USERID == user.getUserID().intValue()) {
				int anzahl = user.getShoppingCartContainer().getSingleShoppingCart().get(ShoppingCartID).getCount().intValue();
				anzahl++;
				singleShoppingCart.setCount(new BigInteger(""+anzahl));
				singleShoppingCart.setCurrency(currency);
				singleShoppingCart.setLink(Helper.SERVERROOT + "/shoppingcarts/" + ShoppingCartID);
				users.getUser().get(userindex).getShoppingCartContainer().getSingleShoppingCart().add(singleShoppingCart);
				US.xmlSchreiben(users);
			}
		}
		
		
		
		
		
		return shoppingCart;
	}
	
	/*
	 * Change State
	 *   ~> return-Wert: ShoppingCart mit shoppingCartID<~
	 */
//	@POST
//	@Path ("/{ShoppingCartID}")
//	@Produces ( " application/xml" )
	public ShoppingCart updateState(@PathParam("ShoppingCartID") int ShoppingCartID,
			@QueryParam("Location") String State) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		
		int shoppingCartIndex = 0;
		for(String state:STATE) {
			if(State.equals(state)) {
				java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
				while(iterator.hasNext()) {
					shoppingCartIndex = iterator.nextIndex();
					ShoppingCart shoppingCart = iterator.next();
					if(ShoppingCartID == shoppingCart.getShoppingCartID().intValue()) {
						shoppingCarts.getShoppingCart().get(shoppingCartIndex).setState(State);
						xmlSchreiben(shoppingCarts);
						return shoppingCarts.getShoppingCart().get(ShoppingCartID);
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * Update ShipTo
	 *   ~> return-Wert: ShoppingCart mit shoppingCartID<~
	 */
//	@POST
//	@Path ("/{ShoppingCartID}")
//	@Produces ( " application/xml" )
	public ShoppingCart updateShipTo(@PathParam("ShoppingCartID") int ShoppingCartID,
			String familyName,
			String firstName,
			String street,
			String number,
			String postalCode,
			String city,
			String province,
			String country,
			String telephone,
			String email) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		
		int shoppingCartIndex = 0;
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			shoppingCartIndex = iterator.nextIndex();
			ShoppingCart shoppingCart = iterator.next();
			if(ShoppingCartID == shoppingCart.getShoppingCartID().intValue()) {
				shoppingCarts.getShoppingCart().get(shoppingCartIndex).setShipTo(Helper.createAdress(familyName,firstName,street,number,postalCode,city,province,country,telephone,email));
				xmlSchreiben(shoppingCarts);
				return shoppingCarts.getShoppingCart().get(ShoppingCartID);
			}
		}
		return null;
	}
	
	/*
	 * Update Price
	 *   ~> return-Wert: ShoppingCart mit shoppingCartID<~
	 */
//	@POST
//	@Path ("/{ShoppingCartID}")
//	@Produces ( " application/xml" )
	public ShoppingCart updatePrice(@PathParam("ShoppingCartID") int ShoppingCartID,
		@QueryParam("Price") double price) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		
		int shoppingCartIndex = 0;
		
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			shoppingCartIndex = iterator.nextIndex();
			ShoppingCart shoppingCart = iterator.next();
			if(ShoppingCartID == shoppingCart.getShoppingCartID().intValue()) {
				shoppingCarts.getShoppingCart().get(shoppingCartIndex).setPrice(new BigDecimal(price));
				xmlSchreiben(shoppingCarts);
				return shoppingCarts.getShoppingCart().get(ShoppingCartID);
			}
		}
		return null;
	}
	
	/*
	 * Update Payment
	 *   ~> return-Wert: ShoppingCart mit shoppingCartID<~
	 */
//	@POST
//	@Path ("/{ShoppingCartID}")
//	@Produces ( " application/xml" )
	public ShoppingCart updatePayment(@PathParam("ShoppingCartID") int ShoppingCartID,
		@QueryParam("Payment") String payment) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		
		int shoppingCartIndex = 0;
		
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			shoppingCartIndex = iterator.nextIndex();
			ShoppingCart shoppingCart = iterator.next();
			if(ShoppingCartID == shoppingCart.getShoppingCartID().intValue()) {
				shoppingCartIndex = shoppingCart.getShoppingCartID().intValue();
				if("Bank Account".equals(payment)) shoppingCarts.getShoppingCart().get(shoppingCartIndex).setPayment("Bank Account");
				else if("Credit Card".equals(payment)) shoppingCarts.getShoppingCart().get(shoppingCartIndex).setPayment("Credit Card");
				else if("Paypal".equals(payment)) shoppingCarts.getShoppingCart().get(shoppingCartIndex).setPayment("Paypal");
				else shoppingCarts.getShoppingCart().get(shoppingCartIndex).setPayment("n.a.");
				xmlSchreiben(shoppingCarts);
				return shoppingCarts.getShoppingCart().get(ShoppingCartID);
			}
		}
		return null;
	}
	
	/*
	 * Update Currency
	 *   ~> return-Wert: ShoppingCart mit shoppingCartID<~
	 */
//	@POST
//	@Path ("/{ShoppingCartID}")
//	@Produces ( " application/xml" )
	public ShoppingCart updateCurrency(@PathParam("ShoppingCartID") int ShoppingCartID,
		@QueryParam("Currency") String currency) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		
		int shoppingCartIndex = 0;
		
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			shoppingCartIndex = iterator.nextIndex();
			ShoppingCart shoppingCart = iterator.next();
			if(ShoppingCartID == shoppingCart.getShoppingCartID().intValue()) {
				shoppingCarts.getShoppingCart().get(shoppingCartIndex).setCurrency(currency);
				xmlSchreiben(shoppingCarts);
				return shoppingCarts.getShoppingCart().get(ShoppingCartID);
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
//	@QueryParam("Item")
//	@Produces ( " application/xml" )
	public ShoppingCart addItemToShoppingCart(@PathParam("ShoppingCartID") int ShoppingCartID,
			@QueryParam("Item") String Item) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		
		int shoppingCartIndex = 0;
		java.util.ListIterator<ShoppingCart> iterator = shoppingCarts.getShoppingCart().listIterator();
		while(iterator.hasNext()) {
			shoppingCartIndex = iterator.nextIndex();
			ShoppingCart shoppingCart = iterator.next();
			if(ShoppingCartID == shoppingCart.getShoppingCartID().intValue()) {
				shoppingCarts.getShoppingCart().get(shoppingCartIndex).getProducts().getItem().add(Item);
				xmlSchreiben(shoppingCarts);
				
				UserService US = new UserService();
				Users users = US.xmlAuslesen();
				java.util.ListIterator<User> userIterator = users.getUser().listIterator();
				int userindex=0;
				while(userIterator.hasNext()) {
					userindex = userIterator.nextIndex();
					User user = userIterator.next();
					if(Helper.USERID == user.getUserID().intValue()) {
						users.getUser().get(userindex).getShoppingCartContainer().getSingleShoppingCart().get(shoppingCartIndex).getItem().add(Item);
						US.xmlSchreiben(users);
						return shoppingCarts.getShoppingCart().get(ShoppingCartID);
					}
				}
			}
		}		
		return null;
	}
	
	
}