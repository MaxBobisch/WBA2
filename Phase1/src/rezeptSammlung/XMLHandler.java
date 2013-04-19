package rezeptSammlung;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ListIterator;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

//import de.restaurants.xml.*;

public class XMLHandler {

	static final String XMLPFAD = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase1/src/aufgabe4ausgabe.xml";
	static Scanner scan = new Scanner(System.in);
	static ObjectFactory ob=new ObjectFactory();
	static Rezeptsammlung rezeptSammlung = new Rezeptsammlung();  
	
	/* Funktion zum Auslesen einer XML Datei
	 * 		~> return-Wert: Rezeptsammlung, aus der XML Datei <~
	 */	
	private static Rezeptsammlung xmlAuslesen() throws JAXBException, FileNotFoundException {
		Rezeptsammlung rezeptSammlung=ob.createRezeptsammlung();
		JAXBContext context = JAXBContext.newInstance(Rezeptsammlung.class);
		Unmarshaller um = context.createUnmarshaller();
		rezeptSammlung = (Rezeptsammlung) um.unmarshal(new FileReader(XMLPFAD));
		return rezeptSammlung;
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Rezeptsammlung rezeptSammlung -> Rezeptsammlung, die in die XML Datei geschrieben wird.)
	 */
	private static void xmlSchreiben(Rezeptsammlung rezeptSammlung) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Rezeptsammlung.class);
		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		System.out.println("Ausgabe der gemarshalten Daten:");
		m.marshal(rezeptSammlung, System.out);
		
		Writer wr = null;
		try {
			wr = new FileWriter(XMLPFAD);
			m.marshal(rezeptSammlung, wr);
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
	
	public static void xmlAusgeben () {
		String line;
    
    try {
      BufferedReader br = new BufferedReader(new FileReader(XMLPFAD));
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
      br.close();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
	}
	
	/* Funktion zum Schreiben einer XML Datei
	 * 		(Rezeptsammlung rezeptSammlung -> Rezeptsammlung, die in die XML Datei geschrieben wird.)
	 */
	private static Kommentar addKommentar(int rezeptID) throws JAXBException, IOException, DatatypeConfigurationException {
		//Hole XML Daten
		rezeptSammlung = xmlAuslesen ();
		
		Kommentar kommentar = new Kommentar();
		Quelle quelle = new Quelle();
		String inhalt, name, avatar, dateTime;
		XMLGregorianCalendar datum;
		
		
		System.out.println("Bitte geben Sie ihren Namen ein:\n");
		name = scan.nextLine();
		System.out.println("Bitte geben Sie die URL ihres Avatars ein:\n");
		avatar = scan.nextLine();
		System.out.println("Bitte geben Sie das Datum in der Form yyyy-mm-ddThh:mm:ss ein:\n");
		dateTime = scan.nextLine();
		datum = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
		System.out.println("Bitte geben Sie ihren Kommentar ein:\n");
		inhalt = scan.nextLine();
		
		quelle.setAvatar(avatar);
		quelle.setDatum(datum);
		quelle.setName(name);
		kommentar.setInhalt(inhalt);
		kommentar.setQuelle(quelle);
			//Schleife durch alle Rezepte
			ListIterator<Rezept> listItRezept = rezeptSammlung.getRezept().listIterator();
			while(listItRezept.hasNext()) {
				//Gibt es das gesuchte Rezept?
				if(rezeptID == rezeptSammlung.getRezept().get(listItRezept.previousIndex()+1).getRezeptid().intValue()) {
					if(rezeptSammlung.getRezept().get(listItRezept.previousIndex()+1).getKommentare().getKommentar().add(kommentar)) {
						xmlSchreiben(rezeptSammlung);
						return kommentar;
					}
				}
				//listItRezept.next();
			}
			
			return null;
	}
	
	public static Rezept rezeptAusgeben (int rezeptID) throws JAXBException, FileNotFoundException {
//Konsolenausgabe Rezept
	rezeptSammlung = xmlAuslesen();
	JAXBContext context = JAXBContext.newInstance(Rezeptsammlung.class);
	Marshaller m = context.createMarshaller();
	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	Rezept rezept;
	
//Schleife durch alle Rezepte
	ListIterator<Rezept> listItRezept = rezeptSammlung.getRezept().listIterator();
	while(listItRezept.hasNext()) {
		//Gibt es das gesuchte Rezept?
		if(rezeptID == rezeptSammlung.getRezept().get(listItRezept.previousIndex()+1).getRezeptid().intValue()) {
			rezept = rezeptSammlung.getRezept().get(listItRezept.previousIndex()+1);
			m.marshal(rezept, System.out);
			
			return rezept;
		}
		//listItRezept.next();
	}
	return null;
	}
	
		public static void main (String[] args) throws JAXBException, IOException, DatatypeConfigurationException {
			
			int rezeptID;
			
			
			System.out.println("Bitte geben Sie die rezeptID ein:\n");
			String tempID = scan.nextLine();
			rezeptID = Integer.parseInt(tempID);
			
			System.out.println("Wählen Sie:\n[1]um einen Kommentar hinzuzufügen oder\n[2]um das Rezept inkl. aller Kommentare auszugeben.");
			String wahlString = scan.nextLine();
			int wal = Integer.parseInt(wahlString);
			
			while(1==wal || 2==wal) {
				if(1==wal) {
					addKommentar(rezeptID);
				}
				if(2==wal) {
					rezeptAusgeben(rezeptID);
				}
				System.out.println("Wählen Sie:\n[1]um einen Kommentar hinzuzufügen oder\n[2]um das Rezept inkl. aller Kommentare auszugeben.");
				wahlString = scan.nextLine();
				wal = Integer.parseInt(wahlString);
			}
			System.exit(0);
			
			
		}
	
}