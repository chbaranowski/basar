package basar.remote.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basar.dao.PositionDao;
import basar.dao.SellerDao;
import basar.domain.Position;
import basar.domain.Sale;
import basar.domain.Seller;
import basar.domain.logic.SaleService;
import basar.remote.CashpointRemoteService;

@Service("cashpointRemoteService")
public class CashpointRemoteServiceImpl implements CashpointRemoteService {

	private SaleService saleService;
	
	private PositionDao positionDao;
	
	private SellerDao sellerDao;
	
	@Autowired
	public void setSellerDao(SellerDao sellerDao) {
		this.sellerDao = sellerDao;
	}

	@Autowired
	public void setSaleService(SaleService saleService){
		this.saleService = saleService;
	}
	
	@Autowired
	public void setPositionDao(PositionDao positionDao){
		this.positionDao = positionDao;
	}
	
	public void purchase(Sale sale) {
		try{
			saleService.purchase(sale);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public List<Position> getLocalPostionList() {
		return positionDao.getPositionList();
	}

	public Seller getSeller(long basarNumber) {
		return sellerDao.getSeller(basarNumber);
	}

}
