package qa.asc;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qa.asc.util.GetFiles;

import java.util.Collection;
import java.util.List;

public class GetFilesTest {
	String filePath;
	GetFiles getFiles;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Map<String, String> env = System.getenv();
		this.filePath = env.get("GET_FILE_PATH");
	}

	@Test (expected=NoSuchFileException.class)
	public void testGetFilesException() throws IOException, NoSuchFileException {
		this.getFiles = new GetFiles("/invalid/path/");
	}
	
	@Test
	public void testGetFilesExension() throws IOException {
		this.getFiles = new GetFiles(this.filePath);
		Collection<File> fileList = this.getFiles.getFiles("HDP");
		assertTrue(fileList.size() >= 64);		
	}
	
	@Test
	public void testGetFilesRegExp() throws IOException {
		this.getFiles = new GetFiles(this.filePath);
		Collection<File> fileList = this.getFiles.getFilesRegex(".*/TC2_.*\\.HDP");
		assertTrue(fileList.size() >= 64);
	}

}
