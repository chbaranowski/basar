package basar.remote;

import java.util.List;

import basar.domain.Position;
import basar.domain.Sale;
import basar.domain.Seller;

public interface CashpointRemoteService {

	void purchase(Sale sale);
	
	List<Position> getLocalPostionList();
	
	Seller getSeller(long basarNumber);
	
}
