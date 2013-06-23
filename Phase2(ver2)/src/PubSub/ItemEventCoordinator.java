package PubSub;

import stickerApp.*;
import stickerService.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import stickerService.CollectionService;
import stickerService.StickerService;

import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

@SuppressWarnings("rawtypes")
public class ItemEventCoordinator<T> implements ItemEventListener {
	
	public static int leaf = -1;

	@SuppressWarnings("unchecked")
	@Override
	public void handlePublishedItems(ItemPublishEvent items) {
		String[] ausgabe;
		
		// ANFANG leaf = Comment
		if(leaf==0) {
			if (items.getNodeId().contains("Stickers")) {
				List<T> itemList = items.getItems();
				for(T item : itemList) {
					String now = item.toString();
					String[] stickerString = now.split(">");
					ausgabe = stickerString[2].split("<");
					String namespace = "Stickers";
					try {
						new StickerService().addCommentToSticker(Publisher.ID, ausgabe[0]);
					} catch (JAXBException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DatatypeConfigurationException e) {
						e.printStackTrace();}
				}
			}
			
			if (items.getNodeId().contains("Collections")) {
				System.out.println(1);
				List<T> itemList = items.getItems();
				System.out.println(2);
				for(T item : itemList) {
					System.out.println(3);
					String now = item.toString();
					System.out.println(4);
					String[] stickerString = now.split(">");
					System.out.println(5);
					ausgabe = stickerString[2].split("<");
					System.out.println(6);
					String namespace = "Collections";
					try {
						System.out.println(7);
						new CollectionService().addCommentToCollection(Publisher.ID, ausgabe[0]);
						System.out.println(8);
					} catch (JAXBException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DatatypeConfigurationException e) {
						e.printStackTrace();}
				}
			}
			
			if (items.getNodeId().contains("Offers")) {
				List<T> itemList = items.getItems();
				for(T item : itemList) {
					String now = item.toString();
					String[] stickerString = now.split(">");
					ausgabe = stickerString[2].split("<");
					String namespace = "Offers";
					try {
						new OfferService().addCommentToOffer(Publisher.ID, ausgabe[0]);
					} catch (JAXBException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DatatypeConfigurationException e) {
						e.printStackTrace();}
				}
			}
			
			if (items.getNodeId().contains("Photos")) {
				List<T> itemList = items.getItems();
				for(T item : itemList) {
					String now = item.toString();
					String[] stickerString = now.split(">");
					ausgabe = stickerString[2].split("<");
					String namespace = "Photos";
					try {
						new PhotoService().addCommentToPhoto(Publisher.ID, ausgabe[0]);
					} catch (JAXBException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DatatypeConfigurationException e) {
						e.printStackTrace();}
				}
			}
			
			if (items.getNodeId().contains("Users")) {
				List<T> itemList = items.getItems();
				for(T item : itemList) {
					String now = item.toString();
					String[] stickerString = now.split(">");
					ausgabe = stickerString[2].split("<");
					String namespace = "Users";
					try {
						new UserService().addCommentToUser(Publisher.ID, ausgabe[0]);
					} catch (JAXBException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DatatypeConfigurationException e) {
						e.printStackTrace();}
				}
			}
			// ENDE leaf = Comment
			
			
		}
		
		
	}

}
 