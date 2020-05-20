package qa.asc;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import java.nio.file.Paths;
import java.util.Map;

import javax.json.JsonException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qa.asc.tc2.Tc2Mapper;

public class Tc2MapperTest {
	String filePath;
	Tc2Mapper mapper;
	static Logger log = Logger.getLogger(Tc2MapperTest.class.getName());
	
	String jsonString = "{\n" + 
			"	\"Common\": {\n" + 
			"		\"MSH\": {\n" + 
			"			\"messageType\": \"./MSH-9-1\",\n" + 
			"			\"messageTrigger\": \"./MSH-9-2\",\n" + 
			"			\"msgDateTime\": \"/MSH-7\",\n" + 
			"			\"sendingFacility\": \"/MSH-4\",\n" + 
			"			\"receivingFacility\": \"/MSH-6\",\n" + 
			"			\"messageControlId\": \"/MSH-10\"\n" + 
			"		}\n" + 
			"	}\n" + 
			"}";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		/* The env values come from the execution environment
		 * in variables defined in the run configuration.
		 */
		Map<String, String> env = System.getenv();
		String basePath = env.get("MAPPING_FILES");
		String fileName = env.get("MAPPING_FILE_NAME");
		this.filePath = Paths.get(basePath, fileName).toString();
		this.mapper = new Tc2Mapper(log);
		
	}
	
	@Test (expected=FileNotFoundException.class)
	public void testGetMapperFileException() throws IOException, FileNotFoundException, ParseException, JsonException  {
			this.mapper.GetTc2MapperFile("/invalid/path/");		
	}

	@Test
	public void testGetMapperFile()  throws IOException, FileNotFoundException, ParseException, JsonException {
		this.mapper.GetTc2MapperFile(this.filePath);
	}
	
	// Test that the PID can be read.
	@Test
	public void testGetPid() throws FileNotFoundException, JsonException, IOException, ParseException {
		this.mapper.GetTc2MapperFile(this.filePath);
		JSONObject o = ((JSONObject) this.mapper.get("PID"));
		assertTrue(o.keySet().toString().contains("5.1"));
	}
	
	@Test
	public void testGetCommon() throws JsonException {
		this.mapper.setTc2MapperObject(this.jsonString);
		JSONObject o = (JSONObject) this.mapper.get("Common");
		assertTrue(o.keySet().toString().contains("MSH"));
		
	}
	
	

}
