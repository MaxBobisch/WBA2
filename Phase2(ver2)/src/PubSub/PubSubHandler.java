package PubSub;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.Subscription;


public class PubSubHandler {

	private static final String HOST = "MAX-LAPTOP";
	private static final int PORT = 9090;
	private static String topicID;
	static ConnectionConfiguration config = new ConnectionConfiguration(HOST,PORT);

			
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
			

			private PubSubManager createPubSubManager() throws XMPPException {
				
				// Create a connection to the XMPP server.
				Connection con = new XMPPConnection(HOST);
				// Connect to the server
				con.connect();
				PubSubManager mgr = new PubSubManager(con, "pubsub.StickerApp");
				return mgr;
			}
			
			
			
			public Node createTopic(String topicID) throws XMPPException {
				ConfigureForm form = new ConfigureForm(FormType.submit);
				form.setPersistentItems(true);
				form.setDeliverPayloads(true);
				form.setAccessModel(AccessModel.open);
				form.setPublishModel(PublishModel.open);
				form.setNotifyDelete(true);
				form.setSubscribe(true);
				
				Node n = createPubSubManager().createNode(topicID, form);
				return n;
			}
			
			
			private Node getNode(String topicID) throws XMPPException{
				
				// Create a connection to the XMPP server.
				Connection con = new XMPPConnection(HOST);
				// Connect to the server
				con.connect();
				PubSubManager mgr = new PubSubManager(con, "pubsub.StickerApp");
				Node n = mgr.getNode(topicID);
				return n;
			}
			
			
			@SuppressWarnings({ "rawtypes" })
			public void publishPayload(String topicID, PayloadItem payload) throws XMPPException{
				((LeafNode)getNode(topicID)).publish(payload);
			}
			
			
			public void subscribe(String jid, String topicID) throws XMPPException{
				getNode(topicID).subscribe(jid);
			}
			
			
			public void unSubscribe(String jid, String topicID) throws XMPPException{
				getNode(topicID).unsubscribe(jid);
			}
			
			
			public void listener(String topicID) throws XMPPException {		
				getNode(topicID).addItemEventListener(new ItemEventCoordinator<Item>());
			}
			
			public void deListener(String topicID) throws XMPPException {		
				((LeafNode)getNode(topicID)).addItemDeleteListener(new ItemDeleteCoordinator<Item>());
			}
			
			
			public String getTopicID(String topicID) throws XMPPException{
				return getNode(topicID).getId();
			}
			
			public void discoverNodes(String topicID) throws XMPPException {
				System.out.println(createPubSubManager().discoverNodes(topicID).toXML());
			}
			
			public void deleteTopic(String topicID) throws XMPPException {
				createPubSubManager().deleteNode(topicID);
			}
			
			public String discoItems(String topicID) throws XMPPException {
				DiscoverItems disco = ((LeafNode)getNode(topicID)).discoverItems();
				return disco.toXML();
			}
			
			
			public void getCurrentItems(String topicID) throws XMPPException {
				Iterable<Subscription> id = (Iterable<Subscription>) getNode(topicID).getSubscriptions();
				for (Subscription i : id) {
					System.out.println("SubscriptionID: " + i.getId());
					System.out.println("ItemResult: " + ((LeafNode)getNode(topicID)).getItems(i.getId()));
				}
			}
			
			public String getThisSubscriber(String topicID) throws XMPPException {
				 String sub = null;
				 if (getNode(topicID).getSubscriptions().isEmpty()){
					 System.out.println("keine Subscriber");
				 }
				 else {
					 int index = getNode(topicID).getSubscriptions().size()-1;
					 sub = getNode(topicID).getSubscriptions().get(index).getId();
				 }
				 return sub;		
			}
			
			public String getItem(String topicID) throws XMPPException {
				 String give = null;
				 if (getNode(topicID).getSubscriptions().isEmpty()){
					 System.out.println("keine Subscriber");
				 }
				 else {
					 int index = getNode(topicID).getSubscriptions().size()-1;
					 String sub = getNode(topicID).getSubscriptions().get(index).getId();
					 give = ((LeafNode)getNode(topicID)).getItems(sub).toString();
				 }
				 return give;		
			}

			
			public String getSubscriptions(String topicID) throws XMPPException {
				Iterable<Subscription> id = (Iterable<Subscription>) getNode(topicID).getSubscriptions();
				String subs = null;
				for (Subscription i : id) {
					subs = i.getId();
//					System.out.println("SubscriptionID: " + i.getId());
				}
				return subs;
			}


			public static String getTopicID() {
				return topicID;
			}


			public static void setTopicID(String topicID) {
				PubSubHandler.topicID = topicID;
			}
}
