package PubSub;

import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

@SuppressWarnings("rawtypes")
public class ItemEventCoordinator<T> implements ItemEventListener {

	@Override
	public void handlePublishedItems(ItemPublishEvent items) {
		{
//Called whenever an item is published to the node the listener is registered with.
		}

	}

}
