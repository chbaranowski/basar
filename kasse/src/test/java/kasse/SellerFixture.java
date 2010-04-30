package kasse;

import static org.junit.Assert.*;

import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacade;
import fit.ColumnFixture;

public class SellerFixture extends ColumnFixture {

	BasarKasseFacade basarKasse = KasseHolder.INSTANCE.getKasse();
	
	public Long nummer;
	
	public String name(){
		Seller seller = basarKasse.getSeller(nummer);
		assertNotNull(seller);
		return seller.getName();
	}
	
}
