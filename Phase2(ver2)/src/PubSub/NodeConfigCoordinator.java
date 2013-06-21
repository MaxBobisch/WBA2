package PubSub;

import org.jivesoftware.smackx.pubsub.ConfigurationEvent;
import org.jivesoftware.smackx.pubsub.listener.NodeConfigListener;

class NodeConfigCoordinator implements NodeConfigListener
      {
          @Override
          public void handleNodeConfiguration(ConfigurationEvent config)
          {
//Called whenever the node the listener is registered with is configured.              
          }
}
