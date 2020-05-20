package qa.asc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.json.JsonException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qa.asc.config.Config;
import qa.asc.config.ConfigConstantsEnum;

public class ConfigTest {
	Config config = null;
	String configPath;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.config = new Config();
		Map<String, String> env = System.getenv();
		this.configPath = env.get("CONFIG_PATH");
	}

	@Test(expected = FileNotFoundException.class)
	public void testNullConfigPath() throws Throwable {
		
			this.config.readConfigFile(null);
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testFileDoestExist()  throws Throwable {
			this.config.readConfigFile("configPath");
	}
	
	@Test
	public void testReadConfig()  throws Throwable {
		this.config.readConfigFile(this.configPath);
		String mappings_path = (String) config.getConfig().get(ConfigConstantsEnum.MAPPING_FILES.value());
		assertTrue(mappings_path.toLowerCase().endsWith("mappings"));
}

}
