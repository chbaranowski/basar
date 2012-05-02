package basar.domain.logic.impl;

import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import basar.domain.Sale;
import basar.remote.CashpointRemoteService;
import basar.remoteclient.CashpointRemoteClient;

@Service
public class TreasuryBalanceTransformer {

	private List<CashpointRemoteClient> remoteCashpoints;
	
	@Autowired
	public void setRemoteCashpoints(List<CashpointRemoteClient> remoteCashpoints){
		this.remoteCashpoints = remoteCashpoints;
	}
	
	@Async
	public void synchSale(Sale sale){
		for (CashpointRemoteClient client : remoteCashpoints) {
			try{
				CashpointRemoteService service = client.getService();
				if(service != null){
					service.purchase(sale);
				}
			}
			catch(Exception exp)
			{
				System.out.println("Remote Kasse nicht erreicht");
				System.out.println(exp);
			}
		}
	}
	
	public void synchSaleAsync(final Sale sale){
		Thread synchSaleThread = new Thread(new Runnable() {
			
			public void run() {
				for (CashpointRemoteClient client : remoteCashpoints) {
					try{
						CashpointRemoteService service = client.getService();
						if(service != null){
							service.purchase(sale);
						}
					}
					catch(Exception exp)
					{
						System.out.println("Remote Kasse nicht erreicht");
						System.out.println(exp);
					}
				}
			}
		});
		synchSaleThread.start();
	}
	
}
