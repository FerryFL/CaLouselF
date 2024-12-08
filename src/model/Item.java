package model;

public class Item {
    private String itemId;
    private String itemName;
    private String itemCategory;
    private String itemSize;
    private double itemPrice;
    private String itemStatus;
    private boolean itemWishlist;
    private boolean itemOfferStatus;
    
	public Item(String itemId, String itemName, String itemCategory, String itemSize, double 	itemPrice,
			String itemStatus, boolean itemWishlist, boolean itemOfferStatus) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
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
	public String getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public String getItemSize() {
		return itemSize;
	}
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public boolean isItemWishlist() {
		return itemWishlist;
	}
	public void setItemWishlist(boolean itemWishlist) {
		this.itemWishlist = itemWishlist;
	}
	public boolean isItemOfferStatus() {
		return itemOfferStatus;
	}
	public void setItemOfferStatus(boolean itemOfferStatus) {
		this.itemOfferStatus = itemOfferStatus;
	}
    
	
}
