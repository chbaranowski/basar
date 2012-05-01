package basar.domain.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import basar.domain.Seller;
import basar.remote.CashpointRemoteService;
import basar.remoteclient.CashpointRemoteClient;

@Service
public class CashpointRemoteDispatcher {

	private List<CashpointRemoteClient> remoteCashpoints;

	@Autowired
	public void setRemoteCashpoints(List<CashpointRemoteClient> remoteCashpoints) {
		this.remoteCashpoints = remoteCashpoints;
	}

	public Optional<Seller> findSellerBy(long basarNumber) {
		Seller remote = null;
		for (CashpointRemoteClient client : remoteCashpoints) {
			try {
				CashpointRemoteService service = client.getService();
				if (service != null) {
					remote = service.getSeller(basarNumber);
					if (remote != null) {
						break;
					}
				}
			} catch (Throwable e) {
				System.out.println("Remote Kasse nicht erreicht");
				System.out.println(e);
			}
		}
		return Optional.of(remote);
	}

}
