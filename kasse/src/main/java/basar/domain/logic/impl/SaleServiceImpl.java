package basar.domain.logic.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basar.dao.PositionDao;
import basar.dao.SellerDao;
import basar.domain.Position;
import basar.domain.PositionKey;
import basar.domain.PositionType;
import basar.domain.Sale;
import basar.domain.Seller;
import basar.domain.logic.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

	private PositionDao positionDao;
	
	private SellerDao sellerDao;
	
	@Autowired
	public void setPositionDao(PositionDao positionDao){
		this.positionDao = positionDao;
	}
	
	@Autowired
	public void setSellerDao(SellerDao sellerDao){
		this.sellerDao = sellerDao;
	}
		
	@Transactional
	public void purchase(Sale sale) {
		List<Position> positions = sale.getPositions();
		for (Position position : positions) {
			Seller seller = position.getSeller();
			Seller localSeller = sellerDao.getSeller(seller.getBasarNumber());
			if(localSeller == null){
				sellerDao.insertSeller(seller);
			}
			if(position.getPositionKey() == null){
				PositionKey key = positionDao.createPositionKey();
				position.setPositionKey(key);
			}
			position.setCreateTime(new Date());
			positionDao.insertPosition(position);
		}
	}

	@Transactional(readOnly=true)
	public boolean isValideBasarNumber(long number) {
		return sellerDao.getSeller(number) != null;
	}

	@Transactional
	public void storno(Sale sale) {
		List<Position> positions = sale.getPositions();
		for (Position position : positions) {
			if(position.getPositionKey() == null){
				PositionKey key = positionDao.createPositionKey();
				position.setPositionKey(key);
			}
			position.setType(PositionType.STORNO);
			position.setCreateTime(new Date());
			positionDao.insertPosition(position);
		}
	}

}
