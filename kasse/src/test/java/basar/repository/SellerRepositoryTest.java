package basar.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
public class SellerRepositoryTest {

	@Autowired
	SellerRepository sellerRepository;
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testSaveSeller() throws Exception {
		assertNotNull(sellerRepository);
	}
	
}
