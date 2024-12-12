package model;

public class Offer {
	private String offerId, userId, itemId, offerPrice, offerStatus;

	public Offer(String offerId, String userId, String itemId, String offerPrice, String offerStatus) {
		super();
		this.offerId = offerId;
		this.userId = userId;
		this.itemId = itemId;
		this.offerPrice = offerPrice;
		this.offerStatus = offerStatus;
	}

	public String getOfferId() {
		return offerId;
	}

	public String getUserId() {
		return userId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(String offerPrice) {
		this.offerPrice = offerPrice;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

}
