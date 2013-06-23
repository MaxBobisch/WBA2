package PubSub;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;


public class Subscriber {
	public static final String USER = "user1";
	private static final String PASSWORD = "password";
	static PubSubHandler handler;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SASLAuthentication.supportSASLMechanism("PLAIN");
		XMPPConnection.DEBUG_ENABLED = true;
		try {
			handler = new PubSubHandler();
			handler.connect();
			handler.login(USER, PASSWORD);
			if(handler.isConnected()){
				System.out.println("\nConnection Success!!");
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}

	}
public void subscribe (String node) throws XMPPException{
	handler.subscribe(handler.getUser(), node);
}
public static String discover (String node) throws XMPPException{
	handler.getChildElXML(node);
	String result = ("");
	result += ("getItem: "+handler.getItem(node));
	handler.getCurrentItems(node);
	result += (handler.getThisSubscriber(node));
	return result;
}
public String listen (String node) throws XMPPException{
	
	if (handler.getSubscriptions(node) == null) {
	return ("No Subscriptions");
	}
	else{
		handler.listener(node);
	return ("Subscribed to node "+node);
	}
}
public void unsubscribe (String node) throws XMPPException{
	handler.unSubscribe(handler.getUser(), node);
}

}
