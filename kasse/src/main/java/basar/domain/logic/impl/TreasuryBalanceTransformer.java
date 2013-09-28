package basar.domain.logic.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import basar.domain.Sale;
import basar.remote.CashpointRemoteService;
import basar.remoteclient.CashpointRemoteClient;

@Service
public class TreasuryBalanceTransformer {
	
	private static final Logger logger = Logger.getLogger(TreasuryBalanceTransformer.class);

	private List<CashpointRemoteClient> remoteCashpoints;
	
	@Autowired
	public void setRemoteCashpoints(List<CashpointRemoteClient> remoteCashpoints) {
		this.remoteCashpoints = remoteCashpoints;
	}
	
	@Async
	public void synchSale(Sale sale) {
		for (CashpointRemoteClient client : remoteCashpoints) {
			try {
				Optional<CashpointRemoteService> serviceRef = Optional.fromNullable(client.getService());
				if(serviceRef.isPresent()) {
					CashpointRemoteService service = serviceRef.get();
					service.purchase(sale);
				}
			}
			catch(Exception exp) {
				logger.info("Synch failed for remote basar", exp);
			}
		}
	}
	
}
