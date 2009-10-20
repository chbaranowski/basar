package basar.remote;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import basar.domain.Position;
import basar.domain.Sale;
import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacade;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/remoteExample.xml"})
public class BasarKasseImplTest {

	@Autowired
	private BasarKasseFacade basarKasse;
	
	@Test
	public void testPurchase() {
		Seller seller = basarKasse.getSeller(100);
		
		Position position = new Position();
		position.setSeller(seller);
		position.setPrice(202);
		
		Sale sale = new Sale();
		sale.addPosition(position);
		
		basarKasse.purchase(sale);
	}

}
