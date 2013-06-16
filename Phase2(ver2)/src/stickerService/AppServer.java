package stickerService;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;

public class AppServer
{
   public static void main( String[] args ) throws Exception
   {
	   String url = "http://localhost:4434";
	   
	   SelectorThread srv = GrizzlyServerFactory.create(url);
	   
	   System.out.println ( "URL:" + url);
	   Thread.sleep( 1000 * 60 * 100);
	   srv.stopEndpoint();
      /*String url = ( args.length > 0 ) ? args[0] : "http://localhost:4434";
      String sec = ( args.length > 1 ) ? args[1] : "10";*/
   }
}