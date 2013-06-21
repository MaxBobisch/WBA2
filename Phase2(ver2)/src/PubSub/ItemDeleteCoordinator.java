package PubSub;

import org.jivesoftware.smackx.pubsub.ItemDeleteEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;

public class ItemDeleteCoordinator<T> implements ItemDeleteListener {

	@Override
	public void handleDeletedItems(ItemDeleteEvent items) {
		{
//Called whenever an item is deleted from the node the deletelistener is registered with.
		}

	}

	@Override
	public void handlePurge() {
// Called when all items are deleted from a node the listener is registered with.	
	}

}