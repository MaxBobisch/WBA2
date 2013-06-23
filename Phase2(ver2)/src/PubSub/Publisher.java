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


public class Publisher {
	public static final String TOPIC = "Vorschlag";
	private static final String USER = "user0";
	private static final String PASSWORD = "password";
	public static int ID = -1;
	public static int leaf = -1;
	public static PubSubHandler handler;
	
	static String newTopic = null;
	
	/**
	 * @param args
	 * @throws JAXBException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, JAXBException {
		SASLAuthentication.supportSASLMechanism("PLAIN");
		XMPPConnection.DEBUG_ENABLED = true;
		try {
			handler = new PubSubHandler();
			handler.connect();
			handler.login(USER, PASSWORD);
			if(handler.isConnected()){
				System.out.println("\nConnection Success!!");			
			}
		}
		catch (Exception e) {
		  System.out.println("FEHLER");
		}
	}
		
public void createTopic(String newTopic) throws XMPPException{
	handler.createTopic(newTopic);
	handler.discoverNodes(newTopic);
}

public String getTopicID (String get) throws XMPPException  {
	return handler.getTopicID(get);
}

public void deleteAll (String del) throws XMPPException {
	handler.getAffiliation(del);
	handler.discoverNodes(del);
	handler.delAllItems(del);
}

public void deleteItem (String del) throws XMPPException {
	handler.getAffiliation(del);
	handler.discoverNodes(del);
	handler.deleteTopic(del);
}

public String serviceDiscovery (String get) throws XMPPException {
	String result = "";
	result += ("\nTopicId: "+handler.getTopicID(get));
	result += ("\ndiscoItems: "+handler.discoItems(get));
	handler.getChildElXML(get);
	result += ("\nService: "+handler.getServiceName());
	handler.discoverNodes(get);
	handler.getItem(get);
	handler.getSubscriptions(get);
	result += ("\nItemID: "+handler.getFirstItemId(get));
	result += ("\nItemID: " + handler.getLastItemId(get));
	return result;
}

@SuppressWarnings("rawtypes")
public void publish(String node, String type, PayloadItem payload) throws FileNotFoundException, JAXBException, XMPPException{
	if(type.equals("Comment")) {
		
		ID = Integer.parseInt("0");
		SimplePayload result = null;
		if("Collections".equals(node) && new CollectionService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/collections", "<collection>"+payload+"</collection>");
		if("Offers".equals(node) && new OfferService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/offers", "<offer>"+payload+"</offer>");
		if("Photos".equals(node) && new PhotoService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/photos", "<photo>"+payload+"</photo>");
		if("Stickers".equals(node) && new StickerService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/stickers", "<sticker>"+payload+"</sticker>");
		if("Users".equals(node) && new UserService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/users", "<user>"+payload+"</user>");
		if(payload != null) {
			PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, result);
			handler.publishPayload(node, payloaditem);
		}
	}
	
	else if(type.equals("Like")) {
			SimplePayload result = null;
			if("Collections".equals(node) && new CollectionService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/collections", "<collection>"+ID+"</collection>");
			if("Offers".equals(node) && new OfferService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/offers", "<offer>"+ID+"</offer>");
			if("Photos".equals(node) && new PhotoService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/photos", "<photo>"+ID+"</photo>");
			if("Stickers".equals(node) && new StickerService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/stickers", "<sticker>"+ID+"</sticker>");
			if("Users".equals(node) && new UserService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/users", "<user>"+ID+"</user>");
			if(payload != null) {
				PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, result);
				handler.publishPayload(node, payloaditem);
		}
	}
	
	else if(type.equals("Title")) {
		SimplePayload result = null;
		if("Collections".equals(node) && new CollectionService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/collections", "<collection>"+payload+"</collection>");
		if("Offers".equals(node) && new OfferService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/offers", "<offer>"+payload+"</offer>");
		if("Photos".equals(node) && new PhotoService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/photos", "<photo>"+payload+"</photo>");
		if("Stickers".equals(node) && new StickerService().contains(ID)) result = new SimplePayload(node,"http://www.example.org/stickers", "<sticker>"+payload+"</sticker>");
		if(payload != null) {
			PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, result);
			handler.publishPayload(node, payloaditem);
		}
	}
}
}
