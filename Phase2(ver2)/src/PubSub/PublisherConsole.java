package PubSub;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.xml.bind.JAXBException;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.SimplePayload;

import stickerService.CollectionService;
import stickerService.OfferService;
import stickerService.PhotoService;
import stickerService.StickerService;
import stickerService.UserService;


public class PublisherConsole {
	public static final String TOPIC = "Vorschlag";
	private static final String USER = "user0";
	private static final String PASSWORD = "password";
	public static int ID = -1;
	public static int leaf = -1;
	
	static String newTopic = null;
	
	/**
	 * @param args
	 * @throws JAXBException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException, JAXBException {
		SASLAuthentication.supportSASLMechanism("PLAIN");
		XMPPConnection.DEBUG_ENABLED = true;
		try {
			PubSubHandler handler = new PubSubHandler();
			handler.connect();
			handler.login(USER, PASSWORD);
			if(handler.isConnected()){
				System.out.println("\nConnection Success!!");
			}
//			DataHandler data = new DataHandler();
			
			
			while(true){
				Scanner scan = new Scanner(System.in);
				System.out.println("\nChoose:\n\n(p)ublish to node\n(c)reate node\n(d)elete node\ndelete(a)ll nodes\n(g)et TopicID\n(s)ervice discovery");
				String input = scan.next();
				
				if(input.matches("c") || input.matches("C")){
					System.out.println("\n\nChoose node:\n");
					newTopic = scan.next();
					handler.createTopic(newTopic);
					handler.discoverNodes(newTopic);
				}
				else if(input.matches("p") || input.matches("P")) {
					
					System.out.println("\nWhat do you want to publish? (C)omment / (L)ike / (T)itle?");
					String leafString = scan.next();
					
					if(leafString.matches("c") || leafString.matches("C")) {
						leaf=0;
						System.out.println("\n\nChoose node: [possible nodes = {Users, Collections, Offers, Photos, Stickers}]:\n");
						String node = scan.next();
						System.out.println("\n\nChoose id:\n");
						ID = Integer.parseInt(scan.next());
						System.out.println("Text of Comment: ");
						String input1 = scan.next();
						SimplePayload payload = null;
						if("Collections".equals(node) && new CollectionService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/collections", "<collection>"+input1+"</collection>");
						if("Offers".equals(node) && new OfferService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/offers", "<offer>"+input1+"</offer>");
						if("Photos".equals(node) && new PhotoService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/photos", "<photo>"+input1+"</photo>");
						if("Stickers".equals(node) && new StickerService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/stickers", "<sticker>"+input1+"</sticker>");
						if("Users".equals(node) && new UserService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/users", "<user>"+input1+"</user>");
						if(payload != null) {
							PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, payload);
							handler.publishPayload(node, payloaditem);
						}
					}
					
					if(leafString.matches("l") || leafString.matches("L")) {
						leaf=1;
						System.out.println("\n\nChoose node: [possible nodes = {Users, Collections, Offers, Photos, Stickers}]:\n");
						String node = scan.next();
						System.out.println("\n\nChoose id:\n");
						ID = Integer.parseInt(scan.next());
						SimplePayload payload = null;
						if("Collections".equals(node) && new CollectionService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/collections", "<collection>"+ID+"</collection>");
						if("Offers".equals(node) && new OfferService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/offers", "<offer>"+ID+"</offer>");
						if("Photos".equals(node) && new PhotoService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/photos", "<photo>"+ID+"</photo>");
						if("Stickers".equals(node) && new StickerService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/stickers", "<sticker>"+ID+"</sticker>");
						if("Users".equals(node) && new UserService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/users", "<user>"+ID+"</user>");
						if(payload != null) {
							PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, payload);
							handler.publishPayload(node, payloaditem);
						}
					}
					
					if(leafString.matches("t") || leafString.matches("T")) {
						leaf=2;
						System.out.println("\n\nChoose node: [possible nodes = {Collections, Offers, Photos, Stickers}]:\n");
						String node = scan.next();
						System.out.println("\n\nChoose id:\n");
						ID = Integer.parseInt(scan.next());
						System.out.println("Title: ");
						String input1 = scan.next();
						SimplePayload payload = null;
						if("Collections".equals(node) && new CollectionService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/collections", "<collection>"+input1+"</collection>");
						if("Offers".equals(node) && new OfferService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/offers", "<offer>"+input1+"</offer>");
						if("Photos".equals(node) && new PhotoService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/photos", "<photo>"+input1+"</photo>");
						if("Stickers".equals(node) && new StickerService().contains(ID)) payload = new SimplePayload(node,"http://www.example.org/stickers", "<sticker>"+input1+"</sticker>");
						if(payload != null) {
							PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, payload);
							handler.publishPayload(node, payloaditem);
						}
					}
				}
				
				else if(input.matches("d") || input.matches("D")){
					System.out.println("Choose node: ");
					String del = scan.next();
					handler.getAffiliation(del);
					handler.discoverNodes(del);
					handler.deleteTopic(del);
				}
				else if(input.matches("a") || input.matches("A")){
					System.out.println("Choose node: ");
					String del = scan.next();
					handler.getAffiliation(del);
					handler.discoverNodes(del);
					handler.delAllItems(del);
				}
				
				else if(input.matches("g") || input.matches("G")){
					System.out.println("Choose node: ");
					String get = scan.next();
					handler.getTopicID(get);
				}
				else if(input.matches("s") || input.matches("S")){
					System.out.println("Choose node: ");
					String get = scan.next();
					System.out.println("TopicId: "+handler.getTopicID(get));
					System.out.println("discoItems: "+handler.discoItems(get));
					handler.getChildElXML(get);
					System.out.println("Service: "+handler.getServiceName());
					handler.discoverNodes(get);
					handler.getItem(get);
					handler.getSubscriptions(get);
					System.out.println("ItemID: "+handler.getFirstItemId(get));
					System.out.println("ItemID: " + handler.getLastItemId(get));
				}
				else
					System.exit(1);
				
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		

	}

}
