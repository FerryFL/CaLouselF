package model;

public class Wishlist {
	private String Wishlist_id;
    private String Item_id;
    private String User_id;
    
	public Wishlist(String wishlistId, String itemId, String userId) {
		super();
		Wishlist_id = wishlistId;
		Item_id = itemId;
		User_id = userId;
	}
	
	public String getWishlistId() {
		return Wishlist_id;
	}

	public void setWishlistId(String wishlistId) {
		Wishlist_id = wishlistId;
	}

	public String getItemId() {
		return Item_id;
	}

	public void setItemId(String itemId) {
		Item_id = itemId;
	}

	public String getUserId() {
		return User_id;
	}

	public void setUserId(String userId) {
		User_id = userId;
	}
    
    
}
