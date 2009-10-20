package basar.remoteclient;

import basar.remote.CashpointRemoteService;

public interface CashpointRemoteClient {

	public CashpointRemoteService getService();
	
	public void setService(CashpointRemoteService service);
	
}
