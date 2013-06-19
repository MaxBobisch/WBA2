package stickerService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ws.rs.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import stickerApp.*;

@Path( "/shoppingcarts" )
public class ShoppingCartService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/xml/ShoppingCarts.xml";
	static ObjectFactory ob=new ObjectFactory();
	
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
	
	
	/* Zeigt alle ShoppingCart an.
	 * 		~> return-Wert: Alle ShoppingCart aus der XML Datei <~
	 */
	@GET
	@Produces ( " application/xml" )
	public ShoppingCarts allShoppingCarts() throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		
		return shoppingCarts;
	}
	
	/* Zeigt ShoppingCart mit shoppingCartID an.
	 * 		~> return-Wert: ShoppingCart mit shoppingCartID aus der XML Datei <~
	 */
	@GET
	@Path ("/{shoppingCartID}")
	@Produces ( " application/xml" )
	public ShoppingCart oneShoppingCart(@PathParam("ShoppingCart_ID") int shoppingCartID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		ShoppingCart shoppingCart = shoppingCarts.getShoppingCart().get(shoppingCartID);
		
		return shoppingCart;
	}
	
	/* Lösche ShoppingCart mit ShoppingCartID.
	 * 		~> return-Wert:  <~
	 */
	@DELETE
	@Path ("/{ShoppingCartID}")
	@Produces ( " application/xml" )
	public ShoppingCarts deleteShoppingCart(@PathParam("ShoppingCart_ID") int ShoppingCartID) throws JAXBException, IOException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		ShoppingCart shoppingCart = shoppingCarts.getShoppingCart().remove(ShoppingCartID);
		xmlSchreiben(shoppingCarts);
		return shoppingCarts;
	}
	
	public Adress createAdress (String familyName,
			String firstName,
			String street,
			String number,
			String postalCode,
			String city,
			String province,
			String country,
			BigInteger telephone,
			String email) {
		Adress adress = new Adress();
		adress.setCity(city);
		adress.setEmail(email);
		adress.setFamilyName(familyName);
		adress.setFirstName(firstName);
		adress.setNumber(number);
		adress.setPostalCode(postalCode);
		adress.setStreet(street);
		if(!"".equals(province)) adress.setProvince(province);
		adress.setCountry(country);
		if(!"".equals(telephone)) adress.setTelephone(new BigInteger(""+telephone));
		if(!"".equals(email)) adress.setEmail(email);
		return adress;
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
			BigInteger telephone,
			String email) throws JAXBException, IOException {
		//Hole XML Daten
		ShoppingCarts shoppingCarts=xmlAuslesen();
		//neues ShoppingCart
		ShoppingCart shoppingCart = new ShoppingCart();
		int lastindex = shoppingCarts.getShoppingCart().lastIndexOf(shoppingCart);
		lastindex++;
		BigInteger ID = new BigInteger("" + lastindex);
		shoppingCart.setProducts(new Products());
		Adress adress = createAdress(familyName,firstName,street,number,postalCode,city,province,country,telephone,email);
		shoppingCart.setShipTo(adress);
		shoppingCart.setPrice(new BigDecimal("" + price));
		shoppingCart.setPayment("n.a.");
		if("Bank Account".equals(payment)) shoppingCart.setPayment("Bank Account");
		if("Credit Card".equals(payment)) shoppingCart.setPayment("Credit Card");
		if("Paypal".equals(payment)) shoppingCart.setPayment("Paypal");
		shoppingCart.setCurrency(currency);
		shoppingCart.setShoppingCartID(ID);
		shoppingCart.setSelf(Helper.SERVERROOT + "/shoppingCarts/" + lastindex);
		shoppingCart.setShopID(new BigInteger ("" + Helper.SHOPID));
		shoppingCart.setShop(Helper.SERVERROOT + "/users/" + Helper.SHOPID);
		shoppingCart.setCustomerID(new BigInteger ("" + Helper.CUSTOMERID));
		shoppingCart.setCustomer(Helper.SERVERROOT + "/users/" + Helper.CUSTOMERID);
		//zur ShoppingCartsliste hinzufuegen
		shoppingCarts.getShoppingCart().add(lastindex, shoppingCart);
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
	 * Add Item zu ShoppingCart
	 *   ~> return-Wert: ShoppingCart inkl. neuem Item <~
	 */
	@POST
	@Path ("/{ShoppingCartID}")
	@Produces ( " application/xml" )
	public ShoppingCart addItemToShoppingCart(@PathParam("Photo_ID") int ShoppingCartID,
			@QueryParam("Location") String Item) 
			throws JAXBException, IOException {
		ShoppingCarts shoppingCarts = xmlAuslesen();
		shoppingCarts.getShoppingCart().get(ShoppingCartID).getProducts().getItem().add(Item);
		xmlSchreiben(shoppingCarts);
		
		UserService US = new UserService();
		Users users = US.xmlAuslesen();
		users.getUser().get(Helper.CUSTOMERID).getShoppingCartContainer().getSingleShoppingCart().get(ShoppingCartID).getItem().add(Item);
		US.xmlSchreiben(users);
		
		return shoppingCarts.getShoppingCart().get(ShoppingCartID);
	}
	
	
}