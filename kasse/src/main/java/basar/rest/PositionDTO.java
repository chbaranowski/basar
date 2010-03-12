package basar.rest;

import java.io.Serializable;

public class PositionDTO implements Serializable {

	private long basarNumber;
	
	private double amount;
	
	private String description;

	public void setBasarNumber(long basarNumber) {
		this.basarNumber = basarNumber;
	}

	public long getBasarNumber() {
		return basarNumber;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
