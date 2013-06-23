package PubSub;

import java.util.Scanner;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.Item;


public class SubscriberConsole {
	public static final String USER = "user1";
	private static final String PASSWORD = "password";
	static PubSubHandler con;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SASLAuthentication.supportSASLMechanism("PLAIN");
		XMPPConnection.DEBUG_ENABLED = true;
		try {
			con = new PubSubHandler();
			con.connect();
			con.login(USER, PASSWORD);
			if(con.isConnected()){
				System.out.println("\nConnection Success!!");
			}


			while(true){
				Scanner scan = new Scanner(System.in);
				System.out.println("\nChoose:\n\n(s)ubscribe to node\n(u)nsubscribe to node\n(l)isten to node\n(i)nfo about the topic");
				String input = scan.next();
				if (input.matches("s")) {
					System.out.println("\n\nChoose node:");
					String sub = scan.next();
					con.subscribe(con.getUser(), sub);
					System.out.println(con.getItem(sub));
				}
				else if (input.matches("i")) {
					System.out.println("Choose node:");
					String sub = scan.next();
					con.getChildElXML(sub);
					System.out.println("getItem: "+con.getItem(sub));
					con.getCurrentItems(sub);
					System.out.println(con.getThisSubscriber(sub));
				}
				else if (input.matches("l")) {
					System.out.println("Choose node:");
					String sub = scan.next();
					if (con.getSubscriptions(sub) == null) {
						System.out.println("No Subscriptions");
					}
					else
					con.listener(sub);
				}
				else if (input.matches("u")) {
					System.out.println("Choose node:");
					String sub = scan.next();
					con.unSubscribe(con.getUser(), sub);
				}
				else System.exit(1);
			}

		} catch (XMPPException e) {
			e.printStackTrace();
		}

	}


}
