package PubSub;

import java.util.Collection;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.jivesoftware.smackx.pubsub.listener.NodeConfigListener;

	public class PubSubHandler {
		static String host = "localhost";
		static int port = 5222;
		
	public static Connection createConnection(){
		// Create a connection to the server on a specific port.
		ConnectionConfiguration config = new ConnectionConfiguration(host, port);
		Connection con = new XMPPConnection(config);
		return con;
	}
	
	public static PubSubManager createPubSubManager(){
		// Create a connection to the server on a specific port.
		Connection con = createConnection();
		// Create a pubsub manager using an existing Connection
	    PubSubManager mgr = new PubSubManager(con);
		return mgr;
	}
	
	public static Node createCollectionNode(String nodeID) throws XMPPException{
		// Create a pubsub manager using an existing Connection
	    PubSubManager mgr = createPubSubManager();
	    // Create the node
	    ConfigureForm form = new ConfigureForm(FormType.submit);
	    //Settings for the node
	    form.setAccessModel(AccessModel.open);
	    form.setDeliverPayloads(true);
	    form.setNotifyRetract(true);
	    form.setPersistentItems(true);
	    form.setPublishModel(PublishModel.open);
	    Node node = mgr.createNode(nodeID, form);
		return node;
	}
	
	public static LeafNode createNode(String nodeID) throws XMPPException{
		// Create a pubsub manager using an existing Connection
	    PubSubManager mgr = createPubSubManager();
	    // Create the node
	    ConfigureForm form = new ConfigureForm(FormType.submit);
	    //Settings for the node
	    form.setAccessModel(AccessModel.open);
	    form.setDeliverPayloads(true);
	    form.setNotifyRetract(true);
	    form.setPersistentItems(true);
	    form.setPublishModel(PublishModel.open);
	    LeafNode leaf = (LeafNode) mgr.createNode(nodeID, form);
		return leaf;	
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean subscribeNode(String nodeID) throws XMPPException{
	    //TODO: get JID
		String subscribersJid = "0815";
	    // Create a pubsub manager using an existing Connection
	    PubSubManager mgr = createPubSubManager();
	    // Get the node
	    LeafNode node = mgr.getNode(nodeID);
	    //create and add listeners
	    ItemEventListener listener = new ItemEventCoordinator<Item>();
	    node.addItemEventListener(listener);
	    ItemDeleteListener deleteListener = new ItemDeleteCoordinator();
	    node.addItemDeleteListener(deleteListener);
	    NodeConfigListener configListener = new NodeConfigCoordinator();
	    node.addConfigurationListener(configListener);
	    //subscribe to the node
	    node.subscribe(subscribersJid);
		return true;
	}
	
	public static boolean unsubscribeNode(String nodeID, @SuppressWarnings("rawtypes") ItemEventListener listener, ItemDeleteListener deleteListener, NodeConfigListener configListener) throws XMPPException{
	    //TODO: get JID
		String subscribersJid = "0815";
	   	// Create a pubsub manager using an existing Connection
	    PubSubManager mgr = createPubSubManager();
	    // Get the node
	    LeafNode node = mgr.getNode(nodeID);
	    //remove listener
	    node.removeItemEventListener(listener);
	    node.removeItemDeleteListener(deleteListener);
	    node.removeConfigurationListener(configListener);
		//unsubscribe to the node
	    node.unsubscribe(subscribersJid);
		return true;
		}
	
		//retrieve the existing items from a node:
	public static Collection<? extends Item> getItemsFromNode(String nodeID) throws XMPPException{
	    // Create a pubsub manager using an existing Connection
	    PubSubManager mgr = createPubSubManager();
	    // Get the node
	    LeafNode node = mgr.getNode(nodeID);
	    //Get the items
	    Collection<? extends Item> items = node.getItems();
		return items;
	}
	
		//publishing with no payload
	public static Node publishToNode(String nodeID, String itemID) throws XMPPException{
        // Create a pubsub manager using an existing Connection
        PubSubManager mgr = createPubSubManager();
        // Get the node
        LeafNode node = mgr.getNode(nodeID);
        // Publish an Item with the specified id
        node.send(new Item(itemID));
        return node;
	}

		//publishing with payload
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Node publishToNodePayload(String nodeID, String itemID, String namespace, String payload) throws XMPPException{
        // Create a pubsub manager using an existing Connection
        PubSubManager mgr = createPubSubManager();
        // Get the node
        LeafNode node = mgr.getNode(nodeID);
        // Publish an Item with payload with the specified id
        node.send(new PayloadItem(itemID, new SimplePayload(nodeID, namespace, payload)));
        return node;
	}
}


