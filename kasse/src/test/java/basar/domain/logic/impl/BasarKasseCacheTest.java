package basar.domain.logic.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.derby.iapi.services.cache.CacheManager;
import org.apache.derby.iapi.services.cache.Cacheable;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import basar.dao.DatabaseTest;
import basar.domain.Position;
import basar.domain.PositionKey;
import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacade;
import basar.domain.logic.BasarKasseFacadeTest;

@DirtiesContext
@ContextConfiguration(locations = { 
		"/spring/core-context.xml",
		"/spring/remote-context.xml" })
public class BasarKasseCacheTest extends DatabaseTest {

	@Autowired
	BasarKasseFacade kasse;
	
	@Autowired
	SimpleCacheManager cacheManager;
	
	@Override
	protected String getDataSetName() {
		return BasarKasseFacadeTest.class.getName();
	}
	
	@Test
	public void testCacheLogic() throws Exception {
		Seller seller = kasse.getSeller(100);
		PositionKey positionKey = kasse.createPositionKey();
		Position position = new Position();
		position.setPositionKey(positionKey);
		position.setSeller(seller);
		position.setPrice(1000);
		position.setCreateTime(new Date());
		kasse.insertPosition(position);
		
		Position positionFromDB = kasse.getPosition(positionKey);
		assertEquals(1000, positionFromDB.getPrice());
		
		
		Position positionFromCache = kasse.getPosition(positionKey);
		assertEquals(1000, positionFromCache.getPrice());
		
		kasse.deletePosition(position);
		
		//cacheManager.getCache("positions").clear();
		
		positionFromCache = kasse.getPosition(positionKey);
		assertNull(positionFromCache);
	}

}
