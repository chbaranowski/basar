package basar.domain.logic.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class DocumentServiceImplTest {

	private DocumentServiceImpl documentServiceImpl = new DocumentServiceImpl();
	
	@Test
	public void testFormat() {
		assertEquals("10,00", documentServiceImpl.format(1000));
		assertEquals("1,00", documentServiceImpl.format(100));
		assertEquals("1,01", documentServiceImpl.format(101));
		assertEquals("1,10", documentServiceImpl.format(110));
		assertEquals("1,10", documentServiceImpl.format(110));
		assertEquals("11,10", documentServiceImpl.format(1110));
		assertEquals("11,00", documentServiceImpl.format(1100));
		assertEquals("0,40", documentServiceImpl.format(40));
		assertEquals("0,04", documentServiceImpl.format(4));
		assertEquals("0,00", documentServiceImpl.format(0));
	}

}
