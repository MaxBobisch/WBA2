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
	
	@SuppressWarnings("unchecked")
	@Override
	public void handlePublishedItems(ItemPublishEvent items) {
		String[] ausgabe;
		
		// ANFANG leaf = Comment
		if(Publisher.leaf==0) {
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
				List<T> itemList = items.getItems();
				for(T item : itemList) {
					String now = item.toString();
					String[] stickerString = now.split(">");
					ausgabe = stickerString[2].split("<");
					String namespace = "Collections";
					try {
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
			
			
			// ANFANG leaf = Like
			if(Publisher.leaf==1) {
				if (items.getNodeId().contains("Stickers")) {
					List<T> itemList = items.getItems();
					for(T item : itemList) {
						String now = item.toString();
						String[] stickerString = now.split(">");
						ausgabe = stickerString[2].split("<");
						String namespace = "Stickers";
						try {
							new StickerService().addLikerToSticker(Integer.parseInt(ausgabe[0]));
						} catch (JAXBException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				if (items.getNodeId().contains("Collections")) {
					List<T> itemList = items.getItems();
					for(T item : itemList) {
						String now = item.toString();
						String[] stickerString = now.split(">");
						ausgabe = stickerString[2].split("<");
						String namespace = "Collections";
						try {
							new CollectionService().addLikerToCollection(Integer.parseInt(ausgabe[0]));
							System.out.println(8);
						} catch (JAXBException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
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
							new OfferService().addLikerToOffer(Integer.parseInt(ausgabe[0]));
						} catch (JAXBException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
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
							new PhotoService().addLikerToPhoto(Integer.parseInt(ausgabe[0]));
						} catch (JAXBException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
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
							new UserService().addLikerToUser(Integer.parseInt(ausgabe[0]));
						} catch (JAXBException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				// ENDE leaf = Like
				
			// ANFANG leaf = updateTitle
				if(Publisher.leaf==2) {
					if (items.getNodeId().contains("Stickers")) {
						List<T> itemList = items.getItems();
						for(T item : itemList) {
							String now = item.toString();
							String[] stickerString = now.split(">");
							ausgabe = stickerString[2].split("<");
							String namespace = "Stickers";
							try {
								new StickerService().updateTitleFromSticker(Helper.getUserID(), ausgabe[0]);
							} catch (JAXBException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					
					if (items.getNodeId().contains("Collections")) {
						List<T> itemList = items.getItems();
						for(T item : itemList) {
							String now = item.toString();
							String[] stickerString = now.split(">");
							ausgabe = stickerString[2].split("<");
							String namespace = "Collections";
							try {
								new CollectionService().updateTitleFromCollection(Helper.getUserID(), ausgabe[0]);
								System.out.println(8);
							} catch (JAXBException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
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
								new OfferService().updateTitleFromOffer(Helper.getUserID(), ausgabe[0]);
								} catch (JAXBException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
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
								new PhotoService().updateTitleFromPhoto(Helper.getUserID(), ausgabe[0]);
								} catch (JAXBException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					// ENDE leaf = updateTitle
				}
			}
			
			
		}
		
		
	}

}
 