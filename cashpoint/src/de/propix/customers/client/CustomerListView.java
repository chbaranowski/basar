package de.propix.customers.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.ListDataProvider;

public class CustomerListView extends CellTable<CustomerModel> {
	
	final TextColumn<CustomerModel> basarNumberColumn = new TextColumn<CustomerModel>() {
		@Override
		public String getValue(CustomerModel model) {
			return String.valueOf(model.getBasarNumber());
		}
	};
	
	final TextColumn<CustomerModel> nameColumn = new TextColumn<CustomerModel>() {
		@Override
		public String getValue(CustomerModel model) {
			return model.getName();
		}
	};
	
	final Column<CustomerModel, String> editButtonColumn = new Column<CustomerModel, String>(new ButtonCell()) {
		@Override
		public String getValue(CustomerModel object) {
			return "Edit";
		}		
	};
	
	final Column<CustomerModel, String> deleteButtonColumn = new Column<CustomerModel, String>(new ButtonCell()) {
		@Override
		public String getValue(CustomerModel object) {
			return "Delete";
		}		
	};
	
	final ListDataProvider<CustomerModel> dataProvider = new ListDataProvider<CustomerModel>();
	
	final ListHandler<CustomerModel> columnSortHandler = new ListHandler<CustomerModel>(dataProvider.getList());

	ChangeCustomerListener removeClickListener = new NullChangeCustomerListener();
	
	ChangeCustomerListener editClickListener = new NullChangeCustomerListener();
	
	{
		addColumn(basarNumberColumn, "Basar Nummer");
		addColumn(nameColumn, "Nachname");
		addColumn(editButtonColumn, "");
		addColumn(deleteButtonColumn, "");
		
		editButtonColumn.setFieldUpdater(new FieldUpdater<CustomerModel, String>() {
			@Override
			public void update(int index, CustomerModel model, String value) {
				editClickListener.change(model);
			}
		});
		
		deleteButtonColumn.setFieldUpdater(new FieldUpdater<CustomerModel, String>() {
			@Override
			public void update(int index, CustomerModel model, String value) {
				removeClickListener.change(model);
			}
		});
		
		basarNumberColumn.setSortable(true);
		
		nameColumn.setSortable(true);
		
		columnSortHandler.setComparator(nameColumn, new Comparator<CustomerModel>() {
			@Override
			public int compare(CustomerModel customer1, CustomerModel customer2) {
				 if (customer1 == customer2) {
		              return 0;
		         }
				 if(customer1 != null){
					 return (customer2 != null) ? customer1.getName().compareTo(customer2.getName()) : 1;
				 }
				return -1;
			}
		});
		columnSortHandler.setComparator(basarNumberColumn, new Comparator<CustomerModel>() {
			@Override
			public int compare(CustomerModel customer1, CustomerModel customer2) {
				if (customer1 == customer2) {
		              return 0;
		         }
				 if(customer1 != null){
					 return (customer2 != null) ? customer1.getBasarNumber().compareTo(customer2.getBasarNumber()) : 1;
				 }
				return -1;
			}
		});
		addColumnSortHandler(columnSortHandler);
		getColumnSortList().push(nameColumn);
		
		dataProvider.addDataDisplay(this);
	}
		
	public void setModel(List<CustomerModel> model){
		List<CustomerModel> list = dataProvider.getList();
		list.clear();
		for(CustomerModel customer : model){
			list.add(customer);
		}
	}
	
	public void setEditClickListener(ChangeCustomerListener listener){
		this.editClickListener = listener;
	}
	
	public void setRemoveClickListener(ChangeCustomerListener listener){
		this.removeClickListener = listener;
	}

}
