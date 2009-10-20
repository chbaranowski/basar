package basar.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import basar.domain.Position;
import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacade;
import basar.remote.CashpointRemoteService;
import basar.remoteclient.CashpointRemoteClient;

@Controller("remotePositionController")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class RemotePositionController {

	private List<CashpointRemoteClient> remoteClientList;

	private BasarKasseFacade basarKasseFacade;

	@Autowired
	public void setBasarKasseFacade(BasarKasseFacade basarKasseFacade) {
		this.basarKasseFacade = basarKasseFacade;
	}

	@Autowired
	public void setRemoteClientList(List<CashpointRemoteClient> remoteClientList) {
		this.remoteClientList = remoteClientList;
	}

	public String actionGetRemotePositions() {
		for (CashpointRemoteClient remoteClient : remoteClientList) {
			CashpointRemoteService service = remoteClient.getService();
			if (service != null) {
				try {
					List<Position> localPostionList = service
							.getLocalPostionList();
					for (Position position : localPostionList) {
						Position localPostion = basarKasseFacade
								.getPosition(position.getPositionKey());
						if (localPostion == null) {
							Seller seller = basarKasseFacade.getSeller(position.getSeller().getBasarNumber());
							if(seller == null){
								basarKasseFacade.insertSeller(position.getSeller());
							}
							basarKasseFacade.insertPosition(position);
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		return "ok";
	}

}
