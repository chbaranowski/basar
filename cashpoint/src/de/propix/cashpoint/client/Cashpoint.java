package de.propix.cashpoint.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Cashpoint implements EntryPoint {
	
	static final String SERVICE_URL = "/kasse";

	final TextBox basarNumberTextBox = new TextBox();

	final TextBox amountTextBox = new TextBox();

	final TextBox descriptionTextBox = new TextBox();

	final KeyPressHandler addBillKeyPressHandler = new KeyPressHandler() {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				addPositionToBill();
			}
			else if (event.getCharCode() == KeyCodes.KEY_ENTER) {
				addPositionToBill();
			}
		}

	};

	final FlexTable billTable = new FlexTable();

	final Button payButton = new Button("Bezahlen");

	final Label amountLabel = new Label("0,00");

	final DialogBox dialogBox = new DialogBox();

	final Button closeDialogButton = new Button("Schliesen");
	
	final Label gewinnLabel = new Label("0,00");
	
	final Label umsatzLabel = new Label("0,00");
	
	final Button addButton = new Button("Hinzu");

	public void onModuleLoad() {

		VerticalPanel cashPointFormPanel = new VerticalPanel();

		cashPointFormPanel.add(new Label("Basar Nummer:"));
		cashPointFormPanel.add(basarNumberTextBox);
		basarNumberTextBox.addKeyPressHandler(addBillKeyPressHandler);
		basarNumberTextBox.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				System.out.println("");
			}
		});
		basarNumberTextBox.setStyleName("valid");
		basarNumberTextBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				validateBasarNumber();
			}

		});

		cashPointFormPanel.add(new Label("Betrag:"));
		cashPointFormPanel.add(amountTextBox);
		amountTextBox.addKeyPressHandler(addBillKeyPressHandler);
		amountTextBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				validateAmount();
			}

		});
		amountTextBox.setText("1,00");
		amountTextBox.setStyleName("valid");

		cashPointFormPanel.add(new Label("Beschreibung"));
		cashPointFormPanel.add(descriptionTextBox);
		descriptionTextBox.addKeyPressHandler(addBillKeyPressHandler);
		descriptionTextBox.setStyleName("valid");
		
		cashPointFormPanel.add(addButton);
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addPositionToBill();
			}
			
		});
		
		addTableheader();

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(payButton);

		VerticalPanel amountPanel = new VerticalPanel();
		amountPanel.add(new Label("Gesamt Betrag:"));
		amountPanel.add(amountLabel);

		RootPanel.get("cashPointForm").add(cashPointFormPanel);
		RootPanel.get("cashPointBill").add(billTable);
		RootPanel.get("cashPointToolBar").add(buttonPanel);
		RootPanel.get("cashPointAmount").add(amountPanel);
		
		RootPanel.get("Gewinn").add(gewinnLabel);
		
		RootPanel.get("Umsatz").add(umsatzLabel);
		
		dialogBox.setAnimationEnabled(true);
		dialogBox.setGlassEnabled(true);
		dialogBox.add(closeDialogButton);
		closeDialogButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}

		});

		amountTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == '5' && event.isControlKeyDown()) {
					amountTextBox.setText("0,50");
				} else if (event.getCharCode() == '6'
						&& event.isControlKeyDown()) {
					amountTextBox.setText("1,50");
				} else if (event.getCharCode() == '7'
						&& event.isControlKeyDown()) {
					amountTextBox.setText("2,50");
				}
			}

		});

		payButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.setText("Speichern ...");
				dialogBox.show();
				dialogBox.center();

				RequestBuilder builder = new RequestBuilder(
						RequestBuilder.POST, SERVICE_URL + "/rest/v1/purchase");

				JSONObject purchaseRequest = new JSONObject();
				JSONArray positions = new JSONArray();

				for (int row = 1; row < billTable.getRowCount(); row++) {
					long basarNumber = Long.valueOf(billTable.getText(row, 0));
					double amount = Double.valueOf(billTable.getText(row, 1)
							.replace(',', '.'));
					String description = billTable.getText(row, 2);
					JSONObject position = new JSONObject();
					position.put("basarNumber", new JSONNumber(basarNumber));
					position.put("amount", new JSONNumber(amount));
					position.put("description", new JSONString(description));
					positions.set(row - 1, position);
				}
				purchaseRequest.put("positions", positions);

				try {
					builder.setHeader("Content-Type", "application/json");

					builder.sendRequest(purchaseRequest.toString(),
							new RequestCallback() {

								public void onError(Request request,
										Throwable exception) {
									dialogBox
											.setText("Es ist ein Fehler beim speichern aufgetretten !!!");
								}

								public void onResponseReceived(Request request,
										Response response) {
									if (200 == response.getStatusCode()) {
										billTable.removeAllRows();
										amountLabel.setText("0,0");
										addTableheader();
										
										JSONObject jsonResponse = (JSONObject)JSONParser.parse(response.getText());
										gewinnLabel.setText(jsonResponse.get("gewinn").isString().stringValue());
										umsatzLabel.setText(jsonResponse.get("umsatz").isString().stringValue());
										
										dialogBox.hide();
									} else {
										dialogBox
												.setText("Es ist ein Fehler beim speichern aufgetretten !!!");
									}
								}
							});
				} catch (RequestException e) {
					// TODO Logging
				}
			}

		});
		
		RequestBuilder builder = new RequestBuilder(
				RequestBuilder.POST, SERVICE_URL + "/rest/v1/info");
		try {
			builder.setHeader("Content-Type", "application/json");

			builder.sendRequest(null, new RequestCallback() {

						public void onError(Request request,
								Throwable exception) {
							dialogBox.setText("Backend not avaviable");
						}

						public void onResponseReceived(Request request,
								Response response) {
							if (200 == response.getStatusCode()) {
								JSONObject jsonResponse = (JSONObject)JSONParser.parse(response.getText());
								gewinnLabel.setText(jsonResponse.get("gewinn").isString().stringValue());
								umsatzLabel.setText(jsonResponse.get("umsatz").isString().stringValue());
							} else {
								dialogBox.setText("Backend not avaviable");
							}
						}
					});
		} catch (RequestException e) {
			// TODO Logging
		}
	}

	private void addTableheader() {
		billTable.setText(0, 0, "Basar Number");
		billTable.setText(0, 1, "Betrag");
		billTable.setText(0, 2, "Beschreibung");
		billTable.setText(0, 3, "Storno");
		billTable.getRowFormatter().setStyleName(0, "table-header");
	}

	protected boolean validateAmount() {
		String amount = amountTextBox.getText();
		if (amount.trim().length() == 0) {
			amountTextBox.setStyleName("error");
			return false;
		} else if(amount.trim().equals(",")){
			amountTextBox.setStyleName("error");
			return false;
		}
		else {
			int kommaIndex = amount.indexOf(',');
			if(kommaIndex < 0)
				kommaIndex = amount.indexOf('.');
			if(kommaIndex >= 0){
				String str = amount.substring(kommaIndex+1, amount.length());
				if(str.equals("50") || str.equals("00") || str.equals("0") || str.equals("5")){
					amountTextBox.setStyleName("valid");
					return true;
				}
			}
			else
			{
				amountTextBox.setStyleName("valid");
				return true;
			}
			amountTextBox.setStyleName("error");
			return false;
		}
	}

	boolean doValidate = false;

	boolean isBasarNumberValid = false;

	protected void validateBasarNumber() {
		String url = com.google.gwt.http.client.URL.encode(SERVICE_URL + "/rest/v1/seller/"+ basarNumberTextBox.getText());
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			builder.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
					// TODO Logging
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						basarNumberTextBox.setStyleName("valid");
						isBasarNumberValid = true;
					} else {
						basarNumberTextBox.setStyleName("error");
						basarNumberTextBox.setFocus(true);
						isBasarNumberValid = false;
					}
					command.execute();
				}
			});
		} catch (RequestException e) {
			// TODO Logging
		}
	}
	
	Command command = new Command() {
		
		@Override
		public void execute() {
		}
	};
	
	protected void addPositionToBill() {
		validateBasarNumber();
		command = new Command() {
			public void execute() {
				if (isBasarNumberValid && validateAmount()) {
					String basarNumber = basarNumberTextBox.getText();
					String amount = amountTextBox.getText();
					String description = descriptionTextBox.getText();
					Button removePostionButton = new Button("X");

					billTable.insertRow(1);
					billTable.setText(1, 0, basarNumber);
					billTable.setText(1, 1, amount);
					billTable.setText(1, 2, description);
					billTable.setWidget(1, 3, removePostionButton);
					removePostionButton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							Cell cellForEvent = billTable.getCellForEvent(event);
							double amountValue = priceToDouble(amountLabel.getText()) -
							priceToDouble(billTable.getText(cellForEvent.getRowIndex(), 1));
							amountLabel.setText(String.valueOf(amountValue).replace('.', ','));
							billTable.removeRow(cellForEvent.getRowIndex());
						}

					});

					basarNumberTextBox.setText("");
					amountTextBox.setText("1,00");
					descriptionTextBox.setText("");
					
					double amountValue = priceToDouble(amountLabel.getText())
					+ Double.valueOf(amount.replace(',', '.'));
					amountLabel.setText(String.valueOf(amountValue).replace('.', ','));
					basarNumberTextBox.setFocus(true);

					isBasarNumberValid = false;
				}
				command =  new Command() {
					@Override
					public void execute() {
					}
				};
			}
			
		};
	}

	double priceToDouble(String price) {
		String str = price.replace(',', '.');
		Double d = Double.valueOf(str);
		return d.doubleValue();
	}
}
