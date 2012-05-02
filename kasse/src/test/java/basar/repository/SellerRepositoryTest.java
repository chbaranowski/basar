package basar.repository;

import static basar.domain.SellerBuilder.seller;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import basar.dao.DatabaseTest;
import basar.dao.PositionDao;
import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacadeTest;

@ContextConfiguration(locations = { 
		"/spring/core-context.xml",
		"/spring/remote-context.xml",
		"/spring/jpa-context.xml"})
public class SellerRepositoryTest extends DatabaseTest {

	@Autowired
	SellerRepository sellerRepository;
	
	@Autowired
	PositionDao positionDao;
	
	@Override
	protected String getDataSetName() {
		return BasarKasseFacadeTest.class.getName();
	}
	
	@Test
	public void testFindOne() throws Exception {
		Seller seller = sellerRepository.findOne(100L);
		assertEquals("Test Kunde", seller.getName());
	}
	
	@Test
	public void testSave() throws Exception {
		Seller seller = seller()
			.basarNumber(999L)
			.name("Test 42 User")
		.build();
		seller = sellerRepository.save(seller);
		seller = sellerRepository.findOne(seller.getBasarNumber());
		assertEquals("Test 42 User", seller.getName());
	}
	
	@Test
	public void testFindAll() throws Exception {
		Iterable<Seller> sellers = sellerRepository.findAll(new Sort(Sort.Direction.ASC, "basarNumber"));
		for (Seller seller : sellers) {
			System.out.println(seller);
		}
	}
	
	@Test
	public void testFindByName() throws Exception {
		List<Seller> sellers = sellerRepository.findByName("Test Kunde", new Sort(Sort.Direction.DESC, "basarNumber"));
		for (Seller seller : sellers) {
			System.out.println(seller);
		}
	}

	@Test
	public void testFindOneByBasarNumberAndName() throws Exception {
		Seller seller = sellerRepository.findOneByBasarNumberAndName(100L, "Test Kunde");
		System.out.println(seller);
	}
	
}
