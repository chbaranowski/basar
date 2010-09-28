package basar.domain;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class PositionKeyTest {

	@Test
	public void testEquals() {
		PositionKey key = new PositionKey();
		key.setKasse("Meine Kasse");
		key.setNumber(1);
		
		assertTrue(key.equals(new PositionKey("Meine Kasse", 1)));
	}
	
	@Test
	public void testEquals_NumberNotEquals() {
		PositionKey key = new PositionKey();
		key.setKasse("Meine Kasse");
		key.setNumber(1);
		
		assertFalse(key.equals(new PositionKey("Meine Kasse", 2)));
	}
	
	@Test
	public void testEquals_KasseNotEquals() {
		PositionKey key = new PositionKey();
		key.setKasse("Andere Kasse");
		key.setNumber(2);
		
		assertFalse(key.equals(new PositionKey("Meine Kasse", 2)));
	}
	
	@Test
	public void testEquals_Null(){
		PositionKey key = new PositionKey();
		assertThat(key.equals(null), is(false));
	}
	
	@Test
	public void testEquals_InvalidType() {
		PositionKey key = new PositionKey();
		assertThat(key.equals("Not a Position"), is(false));
	}

}
