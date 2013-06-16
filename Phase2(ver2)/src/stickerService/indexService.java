package stickerService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBException;

@Path( "/" )
public class indexService
{
	
	
	static final String SERVERROOT = "localhost:4434";
	static final String HTMLFILE = "F:/max.bobisch@gmx.de/Dropbox/Github [MaxBobisch]/WBA2/Phase2(ver2)/src/html/index.html";

	public String htmlAuslesen() throws IOException {
    FileReader fr = new FileReader(HTMLFILE);
    BufferedReader br = new BufferedReader(fr);

    String zeile = "";
    String gesamt = "";

    while( (zeile = br.readLine()) != null )
    {
      gesamt+=zeile;
    }

    br.close();
    return gesamt;
  }	
	
	
		/* Zeigt eine Navigation
	 * 		~> return-Wert: Alle Collection aus der XML Datei <~
	 */
	@GET
	@Produces ( " text/html" )
	public String index() throws JAXBException, IOException {
		String html = htmlAuslesen();		
		return html;
	}
	
}