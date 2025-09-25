package model;

public class TransactionDetail {
	private String transactionId, flowerId;
	private Integer quantity;
	
	public TransactionDetail(String transactionId, String flowerId, Integer quantity) {
		super();
		this.transactionId = transactionId;
		this.flowerId = flowerId;
		this.quantity = quantity;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getFlowerId() {
		return flowerId;
	}
	public void setFlowerId(String flowerId) {
		this.flowerId = flowerId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	
}
