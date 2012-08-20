package basar.web;

import javax.faces.validator.ValidatorException;

import org.junit.Test;

public class CashPointControllerTest {

	private CashPointController controller = new CashPointController();
	
	@Test
	public void testValidatePreis() {
		controller.validatePreis(null, null, "1,0");
		controller.validatePreis(null, null, "1,00");
		controller.validatePreis(null, null, "0,00");
		controller.validatePreis(null, null, "1");
		controller.validatePreis(null, null, "1,5");
		controller.validatePreis(null, null, "100000");
	}
	
	@Test(expected=ValidatorException.class)
	public void testValidatePreis_ValidationException() {
		controller.validatePreis(null, null, "1,001");
	}
	
	@Test(expected=ValidatorException.class)
	public void testValidatePreis_Letter() {
		controller.validatePreis(null, null, "1,00A");
	}
	
	@Test(expected=ValidatorException.class)
	public void testValidatePreis_Point() {
		controller.validatePreis(null, null, "1.0");
	}

}
