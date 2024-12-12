package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;

public class ItemController {
	
    private ObservableList<Item> items = FXCollections.observableArrayList();
    private int itemIdCounter = 1;

    public ObservableList<Item> getItems() {
        return items;
    }

    public void addItem(String name, String size, String price, String category) {
    	String itemId = "ITEM-" + String.valueOf(itemIdCounter++);
        String defaultStatus = "Pending"; 
        String defaultWishlist = "0";    
        String defaultOfferStatus = "No Offer"; 

        Item newItem = new Item(itemId, name, size, price, category, defaultStatus, defaultWishlist, defaultOfferStatus);
        items.add(newItem);
    }


    public void updateItem(String id, String name, String size, String price, String category, String status, String wishlist, String offerStatus) {
        for (Item item : items) {
            if (item.getItemId().equals(id)) {
                item.setItemName(name);
                item.setItemSize(size);
                item.setItemPrice(price);
                item.setItemCategory(category);
                item.setItemStatus(status);
                item.setItemWishlist(wishlist);
                item.setItemOfferStatus(offerStatus);
                break;
            }
        }
    }

    public void deleteItem(String id) {
        items.removeIf(item -> item.getItemId().equals(id));
    }
}
