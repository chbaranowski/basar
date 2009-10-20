package basar.domain.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basar.dao.SellerDao;
import basar.domain.Configuration;
import basar.domain.Document;
import basar.domain.DocumentPosition;
import basar.domain.Position;
import basar.domain.PriceFormatUtil;
import basar.domain.Seller;
import basar.domain.logic.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	private SellerDao sellerDao;
	
	private Configuration configuration;

	@Autowired
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Autowired
	public void setSellerDao(SellerDao sellerDao){
		this.sellerDao = sellerDao;
	}
	
	@Transactional(readOnly=true)
	public List<Document> getDocumentList() {
		List<Document> result = new ArrayList<Document>();
		List<Seller> sellerList = sellerDao.getSellerList();
		for (Seller seller : sellerList) {
			Document document = new Document();
			document.setBasarNumber(seller.getBasarNumber());
			document.setName(seller.getName());
			
			long summe = seller.getSumme();
			
			BigDecimal decimal = new BigDecimal(summe);
			BigDecimal rateBig = decimal.divide(new BigDecimal(100)).multiply(new BigDecimal(configuration.getProzent()));
			
			long rate = Math.round(rateBig.doubleValue());
			long proceeds = summe - rate;
			
			document.setProceeds(format(proceeds-100));
			document.setRate(format(rate));
			document.setSumme(format(seller.getSumme()));
			
			document.setProzent(""+configuration.getProzent());
			List<Position> positions = seller.getPositions();
			for (int i = 0; i < positions.size(); i++) {
				Position position = positions.get(i);
				DocumentPosition docPosition = new DocumentPosition();
				docPosition.setPosition(i+1);
				docPosition.setDescription(position.getDescription());
				docPosition.setPrice(format(position.getPrice()));
				document.addPosition(docPosition);
			}
			result.add(document);
		}
		return result;
	}

	protected String format(long number) {
		return PriceFormatUtil.formatPriceLongToString(number);
	}
	
	

}
