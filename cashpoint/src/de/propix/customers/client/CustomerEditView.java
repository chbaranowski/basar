package de.propix.customers.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CustomerEditView extends VerticalPanel {

	final TextBox basarNumberTextBox = new TextBox();
	
	final TextBox nameTextBox = new TextBox();
	
	final Button saveButton = new Button("Save");
	
	final Button cancelButton = new Button("Cancel");
	
	ChangeCustomerListener saveCustomerClickListener = new NullChangeCustomerListener();
	
	CustomerModel model = new CustomerModel();
	
	{
		add(new Label("Basar Number:"));
		add(basarNumberTextBox);
		
		add(new Label("Name:"));
		add(nameTextBox);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel);
		
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				model.setBasarNumber(Long.valueOf(basarNumberTextBox.getText()));
				model.setName(nameTextBox.getText());
				saveCustomerClickListener.change(model);
			}
		});
	}

	public void setModel(CustomerModel model) {
		this.model = model;
		basarNumberTextBox.setText(model.getBasarNumber().toString());
		nameTextBox.setText(model.getName());
	}

	public void setSaveClickListener(ChangeCustomerListener saveCustomerClickListener) {
		this.saveCustomerClickListener = saveCustomerClickListener;
	}
	
}
