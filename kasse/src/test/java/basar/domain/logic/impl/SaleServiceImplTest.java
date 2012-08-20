package basar.domain.logic.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import basar.dao.PositionDao;
import basar.dao.SellerDao;
import basar.domain.Position;
import basar.domain.PositionKey;
import basar.domain.Sale;
import basar.domain.Seller;

@RunWith(JMock.class)
public class SaleServiceImplTest {
	
	Mockery context = new JUnit4Mockery();
	
	SaleServiceImpl saleService;
	
	SellerDao mockSellerDao;
	
	PositionDao mockPositionDao;
	
	Seller seller23 = new Seller();
	Position positionOne = new Position();
	PositionKey positionOneKey = new PositionKey();
	
	@Before
	public void setUp() throws Exception {
		saleService = new SaleServiceImpl();
		
		mockPositionDao = context.mock(PositionDao.class);
		saleService.setPositionDao(mockPositionDao);
		
		mockSellerDao = context.mock(SellerDao.class);
		saleService.setSellerDao(mockSellerDao);
	}

	@Test
	public void testPurchase() {
		seller23.setBasarNumber(23L);
		positionOne.setSeller(seller23);
		
		context.checking(new Expectations(){{
			allowing(mockSellerDao).getSeller(seller23.getBasarNumber());
			will(returnValue(seller23));
			
			allowing(mockPositionDao).createPositionKey();
			will(returnValue(positionOneKey));
			
			allowing(mockPositionDao).insertPosition(positionOne);
		}});
		
		Sale sale = new Sale();
		sale.addPosition(positionOne);
		
		saleService.purchase(sale);
		
		assertThat(positionOne.getPositionKey(), is(positionOneKey));
	}

}
