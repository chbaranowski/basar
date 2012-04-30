package basar.domain.logic.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basar.dao.PositionDao;
import basar.dao.SellerDao;
import basar.domain.Document;
import basar.domain.Position;
import basar.domain.PositionKey;
import basar.domain.PositionType;
import basar.domain.Sale;
import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacade;
import basar.domain.logic.DocumentService;
import basar.domain.logic.SaleService;

@Service("basarKasse")
public class BasarKasseImpl implements BasarKasseFacade {

	private PositionDao positionDao;

	private SellerDao sellerDao;

	private SaleService saleService;

	private DocumentService documentService;

	private TreasuryBalanceTransformer transformer;

	private CashpointRemoteDispatcher cashpointRemoteDispatcher;

	@Autowired
	public void setCashpointRemoteDispatcher(
			CashpointRemoteDispatcher cashpointRemoteDispatcher) {
		this.cashpointRemoteDispatcher = cashpointRemoteDispatcher;
	}

	@Autowired
	public void setTransformer(TreasuryBalanceTransformer transformer) {
		this.transformer = transformer;
	}

	@Autowired
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	@Autowired
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	@Autowired
	public void setSellerDao(SellerDao sellerDao) {
		this.sellerDao = sellerDao;
	}

	@Autowired
	public void setSaleService(SaleService saleService) {
		this.saleService = saleService;
	}

	public void deleteSeller(Seller seller) {
		sellerDao.deleteSeller(seller);
	}

	public Seller getSeller(long basarNumber) {
		Seller seller = sellerDao.getSeller(basarNumber);
		if (seller == null) {
			seller = cashpointRemoteDispatcher.findSellerBy(basarNumber);
			if (seller != null) {
				sellerDao.insertSeller(seller);
			}
		}
		return seller;
	}

	public List<Seller> getSellerList() {
		return sellerDao.getSellerList();
	}

	public void insertSeller(Seller seller) {
		sellerDao.insertSeller(seller);
	}

	public void updateSeller(Seller seller) {
		sellerDao.updateSeller(seller);
	}

	public boolean isValideBasarNumber(long number) {
		return saleService.isValideBasarNumber(number);
	}

	public void purchase(final Sale sale) {
		saleService.purchase(sale);
		transformer.synchSale(sale);
	}

	public List<Document> getDocumentList() {
		return documentService.getDocumentList();
	}

	public List<Position> getPositionList() {
		return positionDao.getPositionList();
	}

	public void deletePosition(Position position) {
		positionDao.deletePosition(position);
	}

	public void updatePosition(Position position) {
		positionDao.updatePosition(position);
	}

	public void storno(Sale sale) {
		saleService.storno(sale);
	}

	public Position getPosition(PositionKey positionKey) {
		return positionDao.getPosition(positionKey);
	}

	public void insertPosition(Position position) {
		positionDao.insertPosition(position);
	}

	public String getUmsatz() {
		long umsatz = getUmsatzAsLong();
		String str = formatUmsatz(umsatz);
		return str;
	}

	private long getUmsatzAsLong() {
		long umsatz = 0;
		List<Position> positionList = positionDao.getPositionList();
		for (Position position : positionList) {
			if (position.getType().equals(PositionType.SALE))
				umsatz += position.getPrice();
			else
				umsatz -= position.getPrice();
		}
		return umsatz;
	}

	public String formatUmsatz(long umsatz) {
		if (umsatz == 0)
			return "0,00";
		String str = String.valueOf(umsatz);
		if (str.length() == 1)
			return "0,0" + str;
		if (str.length() == 2)
			return "0," + str;
		str = str.substring(0, str.length() - 2) + ","
				+ str.substring(str.length() - 2);
		return str;
	}

	public String getGewinn() {
		long umsatz = getUmsatzAsLong();
		BigDecimal prozent = new BigDecimal(umsatz).divide(new BigDecimal(100));
		long gewinn = Math.round(prozent.doubleValue() * 20);
		return formatUmsatz(gewinn);
	}

}
