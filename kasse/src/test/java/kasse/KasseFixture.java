package kasse;

import java.util.ArrayList;
import java.util.List;

import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacade;
import fit.Fixture;

public class KasseFixture extends Fixture{
	
	BasarKasseFacade basarKasse = KasseHolder.INSTANCE.getKasse();
	
	Seller seller;
	
	public void clear(){
		List<Seller> sellerList = basarKasse.getSellerList();
		ArrayList<Long> removeSellerList = new ArrayList<Long>();
		for (Seller seller : sellerList) {
			removeSellerList.add(seller.getBasarNumber());
		}
		for (Long basarNumber : removeSellerList) {
			Seller seller = basarKasse.getSeller(basarNumber);
			basarKasse.deleteSeller(seller);
		}
	}
	
	public void newSeller(){
		seller = new Seller();
	}
	
	public void sellerName(String sellerName){
		seller.setName(sellerName);
	}
	
	public void sellerNummer(long nummer){
		seller.setBasarNumber(nummer);
	}
	
	public void saveSeller(){
		basarKasse.insertSeller(seller);
	}
	
	public int sellerCount(){
		return basarKasse.getSellerList().size();
	}

}
