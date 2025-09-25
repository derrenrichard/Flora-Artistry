package model;

public class Cart {
	private String userId;
	private Flower flower;
	private Integer quantity;
	
	public Cart(String userId, Flower flower, Integer quantity) {
		super();
		this.userId = userId;
		this.flower = flower;
		this.quantity = quantity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Flower getFlower() {
		return flower;
	}

	public void setFlower(Flower flower) {
		this.flower = flower;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
