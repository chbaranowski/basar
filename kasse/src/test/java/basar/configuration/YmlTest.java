package basar.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.yaml.snakeyaml.JavaBeanLoader;
import org.yaml.snakeyaml.TypeDescription;

import basar.config.Configuration;
import basar.config.Database;
import basar.config.Kasse;

public class YmlTest {


	@Test
	public void testReadConfig() throws Exception {
		URL resource = getClass().getResource("/configuration.yml");
		assertNotNull(resource);
		
		InputStream ins = resource.openStream();
		
		TypeDescription typeDescription = new TypeDescription(Configuration.class, "!configuration");
		JavaBeanLoader<Configuration> loader = 
			new JavaBeanLoader<Configuration>(typeDescription);
		
		Configuration configuration = loader.load(ins);
		
		Database database = configuration.getDatabase();
		assertEquals("root", database.getUsername());
		assertEquals("test", database.getPassword());
		
		Kasse kasse = configuration.getKasse();
		assertEquals("fabienneKasse", kasse.getName());
		assertEquals(20, kasse.getProzent());
	}
	
	
	
	
}
