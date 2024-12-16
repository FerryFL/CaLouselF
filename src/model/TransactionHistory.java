package model;

public class TransactionHistory {
	
	private String transactionID;
	private String itemName;
	private String itemCategory;
	private String itemSize;
	private String itemPrice;
	
	public TransactionHistory(String transactionID, String itemName, String itemCategory, String itemSize,
			String itemPrice) {
		super();
		this.transactionID = transactionID;
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
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
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	
}
