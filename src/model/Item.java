package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Item {
	
	private static ObservableList<Item> items = FXCollections.observableArrayList();
	
	private String itemId;
    private String itemName;
    private String itemSize;
    private String itemPrice;
    private String itemCategory;
    private String itemStatus = "Pending"; 
    private String itemWishlist = "0";     
    private String itemOfferStatus = "No Offer";

	public Item(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory,
			String itemStatus, String itemWishlist, String itemOfferStatus) {
    
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemStatus = itemStatus;
		this.itemWishlist = itemWishlist;
		this.itemOfferStatus = itemOfferStatus;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemWishlist() {
		return itemWishlist;
	}

	public void setItemWishlist(String itemWishlist) {
		this.itemWishlist = itemWishlist;
	}

	public String getItemOfferStatus() {
		return itemOfferStatus;
	}

	public void setItemOfferStatus(String itemOfferStatus) {
		this.itemOfferStatus = itemOfferStatus;
	}
	
	public ObservableList<Item> getItems() {
	    return items;
	}
	
}
