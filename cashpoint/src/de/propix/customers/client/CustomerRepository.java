package de.propix.customers.client;

import java.util.Arrays;
import java.util.List;

public class CustomerRepository {

	List<CustomerModel> customerModels = Arrays.asList(new CustomerModel[]{
			new CustomerModel(100L, "Baranowski"),
			new CustomerModel(101L, "Schoenhaar")
	});
	
	public List<CustomerModel> getAllCustomers(){
		return customerModels;
	}
	
	public void save(CustomerModel customerModel){
		if(!customerModels.contains(customerModel)){
			customerModels.add(customerModel);
		}
	}
	
}
