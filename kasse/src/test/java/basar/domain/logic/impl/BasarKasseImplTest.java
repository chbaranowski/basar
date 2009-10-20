package basar.domain.logic.impl;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basar.dao.PositionDao;
import basar.domain.Position;

public class BasarKasseImplTest {

	private BasarKasseImpl basarKasse;
	
	private PositionDao mockPositionDao;
	
	@Before
	public void setup(){
		mockPositionDao = createMock(PositionDao.class);
		
		basarKasse = new BasarKasseImpl();
		basarKasse.setPositionDao(mockPositionDao);
	}
	
	@After
	public void teardown(){
		reset(mockPositionDao);
	}
	
	@Test
	public void testGetGewinn() {
		Position position = new Position();
		position.setPrice(189750);
		
		List<Position> positionList = new ArrayList<Position>();
		positionList.add(position);
		
		expect(mockPositionDao.getPositionList()).andReturn(positionList);
		
		replay(mockPositionDao);
		String gewinn = basarKasse.getGewinn();
		
		assertEquals("379,50", gewinn);
	}
	
	@Test
	public void testGetGewinn_UmsatzZero() {
		Position position = new Position();
		position.setPrice(0);
		
		List<Position> positionList = new ArrayList<Position>();
		positionList.add(position);
		
		expect(mockPositionDao.getPositionList()).andReturn(positionList);
		
		replay(mockPositionDao);
		String gewinn = basarKasse.getGewinn();
		
		assertEquals("0,00", gewinn);
	}
	
	@Test
	public void testGetGewinn_UmsatzEinEuro() {
		Position position = new Position();
		position.setPrice(100);
		
		List<Position> positionList = new ArrayList<Position>();
		positionList.add(position);
		
		expect(mockPositionDao.getPositionList()).andReturn(positionList);
		
		replay(mockPositionDao);
		String gewinn = basarKasse.getGewinn();
		
		assertEquals("0,20", gewinn);
	}
	
	@Test
	public void testGetGewinn_Umsatz50Cent() {
		Position position = new Position();
		position.setPrice(50);
		
		List<Position> positionList = new ArrayList<Position>();
		positionList.add(position);
		
		expect(mockPositionDao.getPositionList()).andReturn(positionList);
		
		replay(mockPositionDao);
		String gewinn = basarKasse.getGewinn();
		
		assertEquals("0,10", gewinn);
	}

}
