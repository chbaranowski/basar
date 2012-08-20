package basar.domain.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import basar.dao.DatabaseTest;
import basar.domain.Position;
import basar.domain.Sale;
import basar.domain.Seller;
import basar.domain.logic.impl.BasarKasseImpl;

@ContextConfiguration(locations={"/spring/core-context.xml", "/spring/remote-context.xml"})
public class BasarKasseFacadeTest extends DatabaseTest {
	
	@Autowired
	private BasarKasseFacade kasse;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown(){
		super.tearDown();
	}
	
	@Test
	public void testPurchase() {
		assertNotNull(kasse);
		
		Seller sellerOne = kasse.getSeller(100);
		Seller sellerTwo = kasse.getSeller(101);
		
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
		
		
		kasse.purchase(sale);
		System.out.println("");
	}
	
	@Test
	public void testformatUmsatz() throws Exception {
		BasarKasseImpl basarKasseImpl = new BasarKasseImpl();
		assertEquals("0,00", basarKasseImpl.formatUmsatz(0));
		assertEquals("0,01", basarKasseImpl.formatUmsatz(1));
		assertEquals("0,10", basarKasseImpl.formatUmsatz(10));
		assertEquals("1,01", basarKasseImpl.formatUmsatz(101));
		assertEquals("11,11", basarKasseImpl.formatUmsatz(1111));
	}

}
