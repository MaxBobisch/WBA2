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

@Path( "/" )
public class OfferService
{
	static final String XMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/StickerService.xml";
	static final String SERVERROOT = "localhost:4434";
	static ObjectFactory ob=new ObjectFactory();
	
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
	@Path ("/user/{userID}")
	@Produces ( " application/xml" )
	public User oneUser(@PathParam("User_nummer") int userID) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Users users=xmlAuslesen();
		User user = users.getUser().get(userID);
		
		return user;
	}
	
	
	/* Funktion für den Kellner, Änderung des Status' bezahlt
	 * 		(int UserNummer ==> in welchem User?)
	 *  	(int bestellungNummer ==> welche Bestellung?)
	 * 		(boolean status ==> auf welchen Status?)
	 */
	/*@GET
	@Path("/aendereBezahlt/{User_nummer}/{bestellung_nummer}/{bezahlt}")
	@Produces ("application/xml")
	public boolean aendereBezahlt(@PathParam("User_nummer") int UserNummer, @PathParam("bestellung_nummer") int bestellungNummer, @PathParam("bezahlt") String statusString) throws JAXBException, IOException {
		//Hole XML Daten
		Users users = xmlAuslesen ();
		boolean status;
		
		//Schleife durch alle Users
		ListIterator<User> listItusers = users.getUser().listIterator();
		while(listItusers.hasNext()) {
			//Gibt es das gesuchte User?
			if(UserNummer == users.getUser().get(listItusers.previousIndex()+1).getUserNummer().intValue()) {
				//Schleife durch alle Bestellungen
				ListIterator<Bestellung> listItBest = users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().listIterator();
				while(listItBest.hasNext()) {
					if(bestellungNummer == users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellungNummer().intValue()) {
						//ändere Status auf übergebenen Parameter
						if("true".equals(statusString))
							status = true;
						else if("false".equals(statusString))
							status = false;
						else
							return false;
						users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).setBezahlt(status);
						System.out.println("Der Status Bezahlt wurde geändert!");
						//In XML zurückschreiben
						xmlSchreiben(users);
						return true;
					}
				listItBest.next();
				}
			}
		listItusers.next();
		}
		
		return false;
	}
	
	
	
	/* Funktion für den Kellner, Änderung des Status' Serviert
	 * 		(int UserNummer ==> in welchem User?)
	 *  	(int bestellterArtikelNr ==> welcher bestellte Artikel?)
	 * 		(boolean status ==> auf welchen Status?)
	 */
	/*@GET
	@Path("/aendeuserserviert/{User_nummer}/{bestellterArtikel_nummer}/{serviert}")
	@Produces ("application/xml")
	public boolean aendeuserserviert(@PathParam("User_nummer") int UserNummer,  @PathParam("bestellterArtikel_nummer") int bestellterArtikelNummer, @PathParam("serviert") String statusString) throws JAXBException, IOException {
		//Hole XML Daten
		Users users = xmlAuslesen();
		boolean status;
		//Schleife durch alle Users
		ListIterator<User> listItusers = users.getUser().listIterator();
		while(listItusers.hasNext()) {
			//Gibt es das gesuchte User?
			if(UserNummer == users.getUser().get(listItusers.previousIndex()+1).getUserNummer().intValue()) {
				//Schleife durch alle Bestellungen
				ListIterator<Bestellung> listItBest = users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().listIterator();
				while(listItBest.hasNext()) {
					//Schleife durch alle bestellten Artikel
					ListIterator<BestellterArtikel> listItBestArt = users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().listIterator();
					while(listItBestArt.hasNext()) {
						//Gibt es die gesuchte Tischnummer?
						if(bestellterArtikelNummer == users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().get(listItBestArt.previousIndex()+1).getBestellterArtikelNummer().intValue()){
							//ändere Status auf übergebenen Parameter
							if("true".equals(statusString))
								status = true;
							else if("false".equals(statusString))
								status = false;
							else
								return false;
							users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().get(listItBestArt.previousIndex()+1).setServiert(status);
							System.out.println("Der Status Serviert wurde geändert!");
							//In XML zurückschreiben
							xmlSchreiben(users);
							return true;
						}
					listItBestArt.next();
					}
				listItBest.next();
				}
			}
		listItusers.next();
		}
		
		
		return false;
	}
	
	
	
	/* Funktion für den Koch, Änderung des Status' Servierbereit
	 *  	(int UserNummer ==> in welchem User?)
	 * 		(int bestellterArtikelNummer ==> welcher bestellte Artikel?)
	 * 		(boolean status ==> auf welchen Status?)
	 */
	
	/*@GET
	@Path("/aendeuserservierbereit/{User_nummer}/{bestellterArtikel_nummer}/{servierbereit}")
	@Produces ("application/xml")
	public boolean aendeuserservierbereit(@PathParam("User_nummer") int UserNummer, @PathParam("bestellterArtikel_nummer") int bestellterArtikelNummer, @PathParam("servierbereit") String statusString) throws JAXBException, IOException {
		//Hole XML Daten
		Users users = xmlAuslesen();
		boolean status;
		//Schleife durch alle Users
		ListIterator<User> listItusers = users.getUser().listIterator();
		while(listItusers.hasNext()) {
			//Gibt es das gesuchte User?
			if(UserNummer == users.getUser().get(listItusers.previousIndex()+1).getUserNummer().intValue()) {
				//Schleife durch alle Bestellungen
				ListIterator<Bestellung> listItBest = users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().listIterator();
				while(listItBest.hasNext()) {
					//Schleife durch alle bestellten Artikel
					ListIterator<BestellterArtikel> listItBestArt = users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().listIterator();
					while(listItBestArt.hasNext()) {
						//Gibt es die gesuchte Tischnummer?
						if(bestellterArtikelNummer == users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().get(listItBestArt.previousIndex()+1).getBestellterArtikelNummer().intValue()){
							//ändere Status auf übergebenen Parameter
							if("true".equals(statusString))
								status = true;
							else if("false".equals(statusString))
								status = false;
							else
								return false;
							users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().get(listItBestArt.previousIndex()+1).setServierbereit(status);
							System.out.println("Der Status Servierbereit wurde geändert!");
							//In XML zurückschreiben
							xmlSchreiben(users);
							return true;
						}
					listItBestArt.next();
					}
				listItBest.next();
				}
			}
		listItusers.next();
		}
		return false;
		}
	
	
	
	/* Funktion zum Ändern eines Kellnerrufs
	 * 		(int UserNummer ==> in welchem User?)
	 * 		(int tischNr ==> von welchem Tisch?)
	 * 		(boolean status ==> auf welchen Status?)
	 */
/*	@GET
	@Path("/aendereKellnerruf/{User_nummer}/{tisch_nummer}/{kellnerRuf}")
	@Produces ("application/xml")
	public boolean aendereKellnerRuf(@PathParam("User_nummer") int UserNummer, @PathParam("tisch_nummer") int tischNr, @PathParam("kellnerRuf") String statusString) throws JAXBException, IOException {
		//Hole XML Daten
		Users users = xmlAuslesen();
		boolean status;
		//Schleife durch alle Users
		ListIterator<User> listItusers = users.getUser().listIterator();
		while(listItusers.hasNext()) {
			//Gibt es das gesuchte User?
			if(UserNummer == users.getUser().get(listItusers.previousIndex()+1).getUserNummer().intValue()) {
				//Schleife durch alle Bestellungen
				ListIterator<Bestellung> listItBest = users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().listIterator();
				while(listItBest.hasNext()) {
					//Schleife durch alle bestellten Artikel
					ListIterator<BestellterArtikel> listItBestArt = users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().listIterator();
					while(listItBestArt.hasNext()) {
						//Gibt es die gesuchte Tischnummer?
						if(tischNr == users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().get(listItBestArt.previousIndex()+1).getTisch().getTischNummer().intValue()){
							//ändere Status auf übergebenen Parameter
							if("true".equals(statusString))
								status = true;
							else if("false".equals(statusString))
								status = false;
							else
								return false;
							users.getUser().get(listItusers.previousIndex()+1).getBestellungen().getBestellung().get(listItBest.previousIndex()+1).getBestellterArtikel().get(listItBestArt.previousIndex()+1).getTisch().setKellnerRuf(status);
							System.out.println("Der Status Kellnerruf wurde geändert!");
							//In XML zurückschreiben
							xmlSchreiben(users);
							return true;
						}
					listItBestArt.next();
					}
				listItBest.next();
				}
			}
		listItusers.next();
		}
		
		return false;
	}
	
	
	
	/* Funktion zum Erhöhen der Häufigkeit der bestellten Artikel im Ranking
	 * 		(int artikelNummer ==> Gesuchter Artikel)
	 * 		(int UserNummer ==> in welchem User?)
	 * 		(int Haeufigkeit ==> Wie oft bestellt? (Um diesen Wert wird erhöht.))
	 */
	/*@GET
	@Path("/aendereArtikelhaeufigkeit/{User_nummer}/{artikel_nummer}/{haeufigkeit}")
	@Produces ("application/xml")
	public boolean aendereArtikelHaeufigkeit(@PathParam("User_nummer") int UserNummer, @PathParam("artikel_nummer") int artikelNummer, @PathParam("haeufigkeit") int haeufigkeit) throws JAXBException, IOException {
		//Hole XML Daten
		Users users = xmlAuslesen();
		//Schleife durch alle Users
		ListIterator<User> listItusers = users.getUser().listIterator();
		while(listItusers.hasNext()) {
			//Gibt es das gesuchte User?
			if(UserNummer == users.getUser().get(listItusers.previousIndex()+1).getUserNummer().intValue()) {
				//Schleife durch alle Ranking Elemente
				ListIterator<RankingElement> listItRank = users.getUser().get(listItusers.previousIndex()+1).getRanking().getRankingElement().listIterator();
				while (listItRank.hasNext()) {
					//Gibt es ein Element mit der übergebenen artikelNummer?
					if(artikelNummer == users.getUser().get(listItusers.previousIndex()+1).getRanking().getRankingElement().get(listItRank.previousIndex()+1).getArtikelNummer().intValue()) {
						// definiere value = alte artikelhaeufigkeit + übergebener Parameter
						int value = users.getUser().get(listItusers.previousIndex()+1).getRanking().getRankingElement().get(listItRank.previousIndex()+1).getArtikelHaeufigkeit().intValue() + haeufigkeit;
						//ändere alten Wert
						users.getUser().get(listItusers.previousIndex()+1).getRanking().getRankingElement().get(listItRank.previousIndex()+1).setArtikelHaeufigkeit(new BigInteger(""+value));
						System.out.println("Artikel mit der gesuchten Artikelnummer wurde in der Rankingliste gefunden und geändert!");
						
						//In XML zurückschreiben
						xmlSchreiben(users);
						return true;
					}
				listItRank.next();
				}
			}
		listItusers.next();
		}
		return false;
	}
	
	
	@GET
	@Produces ("application/xml")
	@Path("/erstelleUsers")
	public Users erstelleUsers(
			//@PathParam("UserNr") int id,
			//@FormParam("UserName") String name
			) throws JAXBException, IOException
	{		
		//Root-Element User-Liste erstellen
		ArrayList<User> UserList = new ArrayList<User>();
		Users Users = new Users();
		User User = new User();
		
		//UserNummer set
		User.setUserNummer(new BigInteger("12"));
		//UserName set
		User.setUserName("Tolle Bude");
		//UserAdusersse set
		Adusersse adusersse = new Adusersse();
		adusersse.setStrasse("Heimweg");
		adusersse.setHausnummer(new BigInteger("14"));	
		adusersse.setPlz(new BigInteger("61573"));
		adusersse.setOrt("Aschaffenburgo");		
		adusersse.setTelefon("0123-1361");		
		adusersse.setEmail("oppa@vogel.com");
		adusersse.setWebsite("http://www.deineoma.com");
		//Adusersse mit root verbinden
		User.setAdusersse(adusersse);
						
		//UserMenue set
		Menue menue = new Menue();

			//MenueSparte set
			ArrayList<Sparte> sparteList = new ArrayList<Sparte>();
			Sparte sparte = new Sparte();
			sparte.setSparteName("FastFood");
			
				//SparteArtikel set
				ArrayList<Artikel> artikelList = new ArrayList<Artikel>();
				Artikel artikel = new Artikel();
				artikel.setArtikelNummer(new BigInteger("1"));
				artikel.setArtikelName("Burger");
				artikel.setArtikelPreis(new BigDecimal("1.99"));
				artikel.setArtikelWaehrung("Euro");
		
		//Listen hinzufügen
		artikelList.add(artikel);
		sparte.getArtikel().addAll(artikelList);
		sparteList.add(sparte);
		menue.getSparte().addAll(sparteList);	
		
		menue.setMenueText("Das beste Menue weit und breit!");
		//Menue mit root verbinden
		User.setMenue(menue);
		
		//UserBestellungen set
		ArrayList<Bestellung> bestellungList = new ArrayList<Bestellung>();
		Bestellungen bestellungen = new Bestellungen();

			//Bestellung set
			ArrayList<BestellterArtikel> bestellterArtikelList = new ArrayList<BestellterArtikel>();
			Bestellung bestellung = new Bestellung();
			
				//BestellterArtikel set
				BestellterArtikel bestellterArtikel = new BestellterArtikel();
				bestellterArtikel.setArtikelNummer(new BigInteger("1"));
				bestellterArtikel.setBestellterArtikelNummer(new BigInteger("1"));
					//Tisch set
				    Tisch tisch = new Tisch();
				    tisch.setTischNummer(new BigInteger ("1"));
				    	//Kellner set
				    	Kellner kellner = new Kellner();
				    	kellner.setKellnerNummer(new BigInteger("2"));
				    	kellner.setKellnerName("Hans Wurst");
				    tisch.setKellner(kellner);
				    tisch.setKellnerRuf(true);
				bestellterArtikel.setTisch(tisch);
				bestellterArtikel.setServierbereit(true);
				bestellterArtikel.setServiert(false);
			bestellung.setBezahlt(true);
			bestellung.setBestellungNummer(new BigInteger("123"));
		
		//Listen hinzufügen
		bestellterArtikelList.add(bestellterArtikel);
		bestellung.getBestellterArtikel().addAll(bestellterArtikelList);
		bestellungList.add(bestellung);
		bestellungen.getBestellung().addAll(bestellungList);	
		
		//mit root	
		User.setBestellungen(bestellungen);
			
		//UserRanking set
		ArrayList<RankingElement> rankingElementList = new ArrayList<RankingElement>();
		Ranking ranking = new Ranking();
		
			//RankingElement set
			RankingElement rankingElement = new RankingElement();
			rankingElement.setArtikelNummer(new BigInteger("1"));
			rankingElement.setArtikelHaeufigkeit(new BigInteger("78"));
		
		//Listen hinzufügen
		rankingElementList.add(rankingElement);
		ranking.getRankingElement().addAll(rankingElementList);
		
		ranking.setEmailChef("blubb@bla.de");
		//mit root
		User.setRanking(ranking);
		
		//mit der Arraylist UserList verbinden
		UserList.add(User);
		Users.getUser().addAll(UserList);	
		
		//In XML zurückschreiben
		xmlSchreiben(Users);
		return Users;
	}
	
	/* Zeigt alle Users an.
	 * 		~> return-Wert: Alle Users aus der XML Datei <~
	 */
	/*@GET
	@Produces ( " application/xml" )
	public Users getAll() throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Users users=xmlAuslesen();
		
		return users;
	}
	
	/* Zeigt das User mit der Nr. == User_nummer an.
	 * 		~> return-Wert: Users, nur das gesuchte User beinhaltend oder null <~
	 */
	/*@GET
	@Path("/User/{User_nummer}")
	@Produces("application/xml")
	public User getOne(@PathParam("User_nummer") int i) throws JAXBException, FileNotFoundException {
		//Hole XML Daten
		Users users=xmlAuslesen();
	
		//Schleife durch alle Users
			ListIterator<User> listItusers = users.getUser().listIterator();
			while(listItusers.hasNext()) {
				//Gibt es das gesuchte User?
				if(i == users.getUser().get(listItusers.previousIndex()+1).getUserNummer().intValue()) {
					Users rt = new Users();
						
					//Konsolenausgabe des gefundenen Users
					System.out.println("Folgendes User wurde gefunden und nun einzeln ausgegeben:");
					rt.getUser().add(users.getUser().get(listItusers.previousIndex()+1));
					JAXBContext context = JAXBContext.newInstance(Users.class);
					Marshaller m = context.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					User ausgabeusers = users.getUser().get(listItusers.previousIndex()+1);
					m.marshal(ausgabeusers, System.out);
					
					return ausgabeusers;
				}
			listItusers.next();
			}
		return null;
	}
	
	/* Lösche das User mit der Nr. == User_nummer. 
	 * 		~> return-Wert: Das gelöschte User oder null <~
	 */
	/*@GET
	@Path("/loescheUser/{User_nummer}")
	@Produces("application/xml")
	public User deleteOne(@PathParam("User_nummer") int i) throws JAXBException, IOException {
		//Hole XML Daten
		Users users=xmlAuslesen();
		
		JAXBContext context = JAXBContext.newInstance(Users.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		//Schleife durch alle Users
		ListIterator<User> listItusers = users.getUser().listIterator();
		while(listItusers.hasNext()) {
			//Gibt es das gesuchte User?
			if(i == users.getUser().get(listItusers.previousIndex()+1).getUserNummer().intValue()) {

				// Konsolenausgabe und Löschvorgang des zu löschenden Users
				User loeschusers = users.getUser().get(listItusers.previousIndex()+1);
				System.out.println("Folgendes User wurde gefunden und nun gelöscht:");
				m.marshal(loeschusers, System.out);
				users.getUser().remove(listItusers.previousIndex()+1);
				
				//In XML zurückschreiben
				xmlSchreiben(users);
				
				return loeschusers;
			}
		listItusers.next();
		}
		
		return null;
	}
	*/
	 
   /*@GET @Produces( "text/plain" )
   public String halloText( @QueryParam("name") String name )
   {
      return "Hallo " + name;
   }

   @GET @Produces( "text/html" )
   public String halloHtml( @QueryParam("name") String name )
   {
      return "<html><title>HelloWorld</title><body><h2>Html: Hallo " + name + "</h2></body></html>";
   }*/
}