package de.propix.customers.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class CustomerController implements EntryPoint {

	final CustomerRepository customerRepository = new CustomerRepository();
	
	final CustomerListView customerListView = new CustomerListView();
	
	final CustomerEditView customerEditView = new CustomerEditView();
	
	final ChangeCustomerListener editClickListner = new ChangeCustomerListener() {
		@Override
		public void change(CustomerModel model) {
			setModel(model);
			showEditViewAction();
		}
	};
	
	final ChangeCustomerListener saveCustomerListener = new ChangeCustomerListener() {
		@Override
		public void change(CustomerModel customerModel) {
			setModel(customerModel);
			saveCustomerAction();
		}
	};
	
	CustomerModel model;
	
	Panel rootPanel;

	{
		customerListView.setEditClickListener(editClickListner);
		customerEditView.setSaveClickListener(saveCustomerListener);
	}
	
	@Override
	public void onModuleLoad() {
		rootPanel = RootPanel.get("hello");
		showListViewAction();
	}
	

	void setModel(CustomerModel model) {
		this.model = model;
	}

	void showListViewAction(){
		clear();
		customerListView.setModel(customerRepository.getAllCustomers());
		rootPanel.add(customerListView);
	}

	void showEditViewAction() {
		clear();
		customerEditView.setModel(model);
		rootPanel.add(customerEditView);
	}
	
	void saveCustomerAction() {
		customerRepository.save(model);
		showListViewAction();
	}
	
	private void clear(){
		rootPanel.remove(customerListView);
		rootPanel.remove(customerEditView);
	}

}
