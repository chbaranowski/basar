package basar.remoteclient.impl;

import basar.remote.CashpointRemoteService;
import basar.remoteclient.CashpointRemoteClient;

public class CashpointRemoteClientImpl implements CashpointRemoteClient {

	private CashpointRemoteService service;

	public CashpointRemoteService getService() {
		return service;
	}

	public void setService(CashpointRemoteService service) {
		this.service = service;
	}
	
}
