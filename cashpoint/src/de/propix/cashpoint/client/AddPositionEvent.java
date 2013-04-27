package de.propix.cashpoint.client;

import com.google.gwt.event.shared.GwtEvent;

public class AddPositionEvent extends GwtEvent<PositionEventHandler>{

	public static Type<PositionEventHandler> TYPE = new Type<PositionEventHandler>();
	
	public Position position;
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PositionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PositionEventHandler handler) {
		handler.onAddPosition(this);
	}

}
