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
	static final String SERVERROOT = "localhost:4434";
	static ObjectFactory ob=new ObjectFactory();
	static final int SHOPID = 0;
	static final int CUSTOMERID = 0;
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: ShoppingCarts, aus der XML Datei <~
	 */	
	private ShoppingCarts xmlAuslesen() throws JAXBException, FileNotFoundException {
		ShoppingCarts shoppingCarts=ob.createShoppingCarts();
		JAXBContext context = JAXBContext.newInstance(ShoppingCarts.class);
		Unmarshaller um = context.createUnmarshaller();
		shoppingCarts = (ShoppingCarts) um.unmarshal(new FileReader(XMLFILE));
		return shoppingCarts;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(ShoppingCarts ShoppingCarts -> ShoppingCarts, die in die XML Datei geschrieben werden.)
	 */
	private void xmlSchreiben(ShoppingCarts shoppingCarts) throws JAXBException, IOException {
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
		adress.setCountry(country);
		adress.setEmail(email);
		adress.setFamilyName(familyName);
		adress.setFirstName(firstName);
		adress.setNumber(number);
		adress.setPostalCode(postalCode);
		adress.setProvince(province);
		adress.setStreet(street);
		adress.setTelephone(telephone);
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
		shoppingCart.setSelf(SERVERROOT + "/shoppingCarts/" + lastindex);
		shoppingCart.setShopID(new BigInteger ("" + SHOPID));
		shoppingCart.setShop(SERVERROOT + "/users/" + SHOPID);
		shoppingCart.setCustomerID(new BigInteger ("" + CUSTOMERID));
		shoppingCart.setCustomer(SERVERROOT + "/users/" + CUSTOMERID);
		//zur ShoppingCartsliste hinzufuegen
		shoppingCarts.getShoppingCart().add(lastindex, shoppingCart);
		//ins XML zurückschreiben
		xmlSchreiben(shoppingCarts);
		return shoppingCart;
	}
	
	
}