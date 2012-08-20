package basar.fit;

import static org.junit.Assert.*;

import org.junit.Test;

import fit.FileRunner;

public class RunSampleFitTest {

	@Test
	public void testCreateSellerTest() throws Exception {
		FileRunner runner = new FileRunner();
		runner.args(new String[]{"src/test/fit/CreateSellerTest.html", "target/CreateSellerTest.html"});
		runner.process();
		runner.output.close();
		assertEquals("No exception expected.", 0, runner.fixture.counts.exceptions);
		assertEquals("No errors in expected.", 0, runner.fixture.counts.wrong);
	}
	
}
