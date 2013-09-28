package basar.domain.logic.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import basar.domain.Seller;
import basar.remote.CashpointRemoteService;
import basar.remoteclient.CashpointRemoteClient;

@Service
public class CashpointRemoteDispatcher {
	
	private static final Logger logger = Logger.getLogger(CashpointRemoteDispatcher.class);

	private List<CashpointRemoteClient> remoteCashpoints;

	@Autowired
	public void setRemoteCashpoints(List<CashpointRemoteClient> remoteCashpoints) {
		this.remoteCashpoints = remoteCashpoints;
	}

	public Optional<Seller> findSellerBy(long basarNumber) {
		Seller remote = null;
		for (CashpointRemoteClient client : remoteCashpoints) {
			try {
				Optional<CashpointRemoteService> serviceRef = Optional.fromNullable(client.getService());
				if (serviceRef.isPresent()) {
					CashpointRemoteService service = serviceRef.get();
					remote = service.getSeller(basarNumber);
					if (remote != null) {
						break;
					}
				}
			} catch (Throwable exp) {
				logger.info("find seller on remote basar failed", exp);
			}
		}
		return Optional.fromNullable(remote);
	}

}
