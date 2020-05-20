package qa.asc;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import qa.asc.config.Config;
import qa.asc.config.ConfigConstantsEnum;
import qa.asc.tc2.Tc2Mapper;
import qa.asc.util.GetFiles;
import org.apache.log4j.Logger;


/**
 * Message to JSON parser so we can map values from one format to a machine readable format.
 *
 */
public class Msg2json 
{
	final static Logger log = Logger.getLogger(Msg2json.class.getName());
    public static void main( String[] args )
    {
    	
    	log.debug("Starting Msg2Json ******");
        try {
			Config config = new Config();
			config.readConfigFile(args[0]);

			// get the path to mapping files
			String mappings_path = (String) config.getConfig().get(ConfigConstantsEnum.MAPPING_FILES.value());
			
			// process tc2 file data

			System.out.printf("%s", config.getTC2().get(ConfigConstantsEnum.PATH.value()));
			String path = (String) config.getTC2().get(ConfigConstantsEnum.PATH.value());
			String file_mask = (String) config.getTC2().get(ConfigConstantsEnum.FILE_MASK.value());
			String mapping_file_name = (String) config.getTC2().get(ConfigConstantsEnum.MAPPINGS.value());
			Path filePath = Paths.get(mappings_path, mapping_file_name);
			Tc2Mapper tc2Mapper = new Tc2Mapper(log);
			tc2Mapper.GetTc2MapperFile(filePath.toString());
			GetFiles getFiles = new GetFiles(path);
			for (Object file: getFiles.getFilesRegex(file_mask)) {
				log.debug(String.format("File Name Found: %s",file));
			}
			
		} catch (Exception e) {
			log.error(String.format("Msg2Json %s", e.getStackTrace()));
		}
        log.debug("Msg2Json Run Completed ******");
    }
}
