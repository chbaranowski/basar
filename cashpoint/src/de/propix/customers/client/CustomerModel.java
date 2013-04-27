package de.propix.customers.client;

public class CustomerModel {

	private Long basarNumber;
	
	private String name;

	public CustomerModel() {
	}
	
	public CustomerModel(Long basarNumber, String name) {
		this.basarNumber = basarNumber;
		this.name = name;
	}

	public void setBasarNumber(Long basarNumber) {
		this.basarNumber = basarNumber;
	}

	public Long getBasarNumber() {
		return basarNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
