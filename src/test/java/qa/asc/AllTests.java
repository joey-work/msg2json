package qa.asc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConfigTest.class, GetFilesTest.class, Tc2MapperTest.class, Tc2ParserTest.class })
public class AllTests {

}
