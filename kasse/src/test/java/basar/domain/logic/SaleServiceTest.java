package basar.domain.logic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

import basar.dao.DatabaseTest;
import basar.dao.SellerDao;
import basar.domain.Position;
import basar.domain.Sale;
import basar.domain.Seller;

@ContextConfiguration(locations={"/spring/dao.xml", "/spring/remote.xml"})
public class SaleServiceTest extends DatabaseTest {

	@Autowired
	private SaleService saleService;
	
	@Autowired
	private SellerDao sellerDao;
	
	@Test
	public void testPurchase() {
		Seller sellerOne = sellerDao.getSeller(100);
		Seller sellerTwo = sellerDao.getSeller(101);
		
		Position hose = new Position();
		hose.setDescription("Hose");
		hose.setPrice(2150);
		hose.setSeller(sellerOne);
		
		Position kleid = new Position();
		kleid.setDescription("Kleid");
		kleid.setPrice(2150);
		kleid.setSeller(sellerTwo);
		
		Sale sale = new Sale();
		sale.addPosition(hose);
		sale.addPosition(kleid);
		
		saleService.purchase(sale);
		
		int countRowsInPositionTable = 
			SimpleJdbcTestUtils.countRowsInTable(simpleJdbcTemplate, "position");
		assertEquals(2, countRowsInPositionTable);	
	}

	@Test
	public void testPurchase_emptySale() {
		Sale sale = new Sale();
		saleService.purchase(sale);
		int countRowsInPositionTable = 
			SimpleJdbcTestUtils.countRowsInTable(simpleJdbcTemplate, "position");
		assertEquals(0, countRowsInPositionTable);	
	}
	
	
	@Test
	public void testIsValideBasarNumber() {
		assertTrue(saleService.isValideBasarNumber(100));
	}
	

	@Test
	public void testIsValideBasarNumber_NonValidNumber() {
		assertFalse(saleService.isValideBasarNumber(1001));
	}
	

}
