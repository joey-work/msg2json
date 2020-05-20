/**
 * 
 */
package qa.asc.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator; 
import java.util.Map; 
  
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 
import javax.json.JsonException;
import javax.json.stream.JsonParsingException;

/**
 * @author rob
 *
 */
public class Config {
	private JSONObject jo;
	
	public Config() {
		this.jo = new JSONObject();
	}
	
	public void readConfigFile(String configPath )  throws Exception{
		try {
			if (configPath == null) {
				throw new FileNotFoundException("Read Config File Null File Path");
			}
			Object obj = new JSONParser().parse(new FileReader(configPath));
			
			// typecasting obj to JSONObject 
	        this.jo = (JSONObject) obj; 
			
		}catch (JsonException je) {
			je.printStackTrace();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException(String.format("Error Reading Configuration File %s", e.getMessage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean hasKey(String key) {
		return this.jo.keySet().contains((Object) key);
	}
	
	public JSONObject getConfig() {
		return (JSONObject) this.jo;
	}
	
	public JSONObject getTC2() {
		JSONObject returnValue = null;
		if (this.hasKey(ConfigConstantsEnum.TC2_OBJECT.value())) {
			returnValue = (JSONObject) this.jo.get(ConfigConstantsEnum.TC2_OBJECT.value());
		}
		return returnValue;
		
	}
	
	public JSONObject getAHLATAT () {
		JSONObject returnValue = null;
		if (this.hasKey(ConfigConstantsEnum.AHLTAT_OBJECT.value())) {
			return (JSONObject) this.jo.get(ConfigConstantsEnum.AHLTAT_OBJECT.value());
		}
		return returnValue;
	}
	
	public JSONObject getMCC () {
		JSONObject returnValue = null;
		if (this.hasKey(ConfigConstantsEnum.MCC_OBJECT.value())) {
			returnValue =  (JSONObject) this.jo.get(ConfigConstantsEnum.MCC_OBJECT.value());
		}
		return returnValue;
	}

}
