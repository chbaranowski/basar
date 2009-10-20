package basar.domain.logic;

import basar.domain.Sale;

public interface SaleService {

	boolean isValideBasarNumber(long number);
	
	void purchase(Sale sale);
	
	void storno(Sale sale);
	
}
