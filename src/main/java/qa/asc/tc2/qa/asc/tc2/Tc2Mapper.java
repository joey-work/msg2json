/**
 * 
 */
package qa.asc.tc2;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.util.Terser;
import qa.asc.config.ConfigConstantsEnum;

import org.apache.log4j.Logger;



/**
 * @author rob
 *
 */
public class Tc2Mapper {
	
	JSONObject mapper;
	Logger logger;
	final static int FIELD_NUMBER_OFFSET = 1; //field number (starting at 1)
	final static String RE_DIGITS_ONLY = "^\\d+$";
	final static String RE_DIGITS_DECIMAL = "^(\\d+)[\\-\\.](\\d+)$";
	final static String RE_KNOWN_ELEMENTS = "^(?:section)|(?:optional)|(?:repeat)$";
	HashMap<String, HashMap<String, HashMap<String, String>>> dataValidationMapping;
	HashMap<String, HashMap<String, HashMap<String, String>>> uiMapping;
	HashMap<String, HashMap<String, HashMap<String, String>>> dbMapping;
	
	public Tc2Mapper(Logger logger) {
		this.mapper = null;
		this.logger = logger;
		
		this.dataValidationMapping = new HashMap<String, HashMap<String, HashMap<String, String>>> ();
		this.uiMapping = new HashMap<String, HashMap<String, HashMap<String, String>>> ();
		this.dbMapping = new HashMap<String, HashMap<String, HashMap<String, String>>> ();

	}
	
	public void GetTc2MapperFile(String tc2MapperPath) throws FileNotFoundException {
		try {
			Object obj = new JSONParser().parse(new FileReader(tc2MapperPath));
			
			// typecasting obj to JSONObject 
	        this.mapper = (JSONObject) obj; 
			
		} catch (JsonException je) {
			this.logger.error(String.format("Get Tc2 Mapper File: %s",je.getStackTrace().toString()));
			
		} catch (NoSuchFileException nsfe) {
			throw new FileNotFoundException(String.format("Error Reading TC2 Mapper File %s", nsfe.getMessage()));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(String.format("Error Reading TC2 Mapper File %s", e.getMessage()));
		} catch (IOException | ParseException e) {
			this.logger.error(String.format("Get Tc2 Mapper File: %s",e.getStackTrace().toString()));
		}
	}
	
	public void setTc2MapperObject(String jsonRepresentation) {
		try {
			Object obj = new JSONParser().parse(jsonRepresentation);
			// typecasting obj to JSONObject 
	        this.mapper = (JSONObject) obj; 
		} catch (ParseException e) {
			this.logger.error(String.format("Set Tc2 Mapper Object: %s",e.getStackTrace().toString()));
		}
	}
	
	public JSONObject get(String key) {
		return (JSONObject) this.mapper.get(key);
	}
	
	private Integer indexToNumber(String index) throws NumberFormatException {
		Integer returnValue = -1;
		if (Pattern.compile(RE_DIGITS_ONLY).matcher(index).find()) {
			 returnValue = Integer.parseInt(index);
		} else if (Pattern.compile(RE_DIGITS_DECIMAL).matcher(index).find()) {
			Pattern pattern = Pattern.compile(RE_DIGITS_DECIMAL);
			Matcher matcher = pattern.matcher(index);
			if (matcher.find()) {
				returnValue =  Integer.parseInt(matcher.group(1));
			}
		} else if (!Pattern.compile(RE_KNOWN_ELEMENTS,Pattern.CASE_INSENSITIVE).matcher(index).find()) {
			throw new NumberFormatException(String.format("Failed conversion of Index to Number Index=%s", index));
		}
		
		return returnValue;
	}
	
	/****
	 * Some values are embedded, complex values such as a name or address. This method will get the exact value that should
	 * be returned based on index
	 * @param index
	 * @param value
	 */
	public void getValue(Object value, String index, HashMap <String, String> segment) {
		
		
	}
	
	/***
	 * This performs the mapping from the HashMap to return a JSONObject that can be embedded as the results.
	 * 
	 * 
	 * @param valueMapping
	 * @return
	 */
	public JSONObject getJSONObjectFromValues(HashMap<String, HashMap<String, String>> valueMapping) {
		
		return new JSONObject();
	}
	
	/***
	 * Used to add a UIID mapping for output in the JSON object
	 * @param section
	 * @param repeat
	 * @param value
	 */
	public void addUIValueToMessageSegment(String section, boolean repeat, String uuidName, Object value) {
		
	}
	
	/***
	 * Used to add a DB mapping for output in the JSON object
	 * 
	 * @param section
	 * @param repeat
	 * @param uuidName
	 * @param value
	 */
	public void addDBValueToMessageSegment(String section, boolean repeat, String uuidName, Object value) {
		
	}
	
	
	
	/***
	 * 
	 * { "PATIENT KEY": { "ssn": "", "firstName": "", "lastName":""},
	 * 		"<PAGE>": {
	 * 				"<SECTION>":{
	 * 					"<TABLE>": [{"<UIID>": "",
	 * 								 "<UIID>": "",
	 * 								}
	 * 							 ]
	 * 				}
	 * 	
	 * 
	 * 
	 * 				}
	 * }
	 * 
	 * { "MSH": {
	 * 			},
	 * 	 "PID": {
	 * 			}
	 * }
	 * 
	 * 
	 * @param segment
	 * @param msgSegment
	 * @param segmentName
	 * @return
	 * @throws Exception
	 */
	public String mapSegmentToValues(JSONObject segment, Segment msgSegment, String segmentName) throws Exception {
			
		String[] names = msgSegment.getNames();
		for (Object index: segment.keySet()) {
			try {
				Integer intIndex = this.indexToNumber(index.toString());
				
				String section = (String) segment.get(ConfigConstantsEnum.SECTION.value());
				boolean optional = (boolean) segment.get(ConfigConstantsEnum.OPTIONAL.value().toLowerCase().equals(ConfigConstantsEnum.TRUE.value()));
				boolean repeat = (boolean) segment.get(ConfigConstantsEnum.REPEAT.value().toLowerCase().equals(ConfigConstantsEnum.TRUE.value()));
				
				
				if (intIndex <= msgSegment.numFields() && intIndex >= 0) {
					Type[] values = msgSegment.getField(intIndex); 
					String name = names[intIndex - FIELD_NUMBER_OFFSET];
					for (Type value: values) {
						HashMap <String, String> segmentHM = (HashMap) segment.get(index);
						//this.getValue((Object) value, (String) index, segmentHM);
						String uuidName =  (String) ((HashMap) segment.get(index)).get(ConfigConstantsEnum.UIID.value());
						String uiName = (String) ((HashMap) segment.get(index)).get(ConfigConstantsEnum.UI.value());
						String dbName = (String) ((HashMap) segment.get(index)).get(ConfigConstantsEnum.DB.value());
						this.logger.debug(String.format("mapSegmentToValues: Segment Name = %s Index = %s Name = %s UUID = %s Value = %s Label = %s DB = %s", segmentName, index, name, uuidName, value.toString(), uiName, dbName));
					}
				}
			} catch (NumberFormatException nfe) {
				this.logger.error(String.format("Map Segment to Values Segment Key Not Number (%s)", nfe.getMessage()));
				
			} catch (Exception ex) {
				throw new Exception(String.format("Map Segment to Values Exception: (%s)", ex.getMessage()));
			}
		}
		return "";
	}
	
	/***
	 * Tests the terser to determine if it contains the segment. 
	 * 
	 * Using an exception isn't good form but the terser object doesn't 
	 * provide a "contains" method to determine if the terser does contain
	 * a specific segment, so this isolates the gap in terser.
	 * @param tc2Parser
	 * @param spec
	 * @return
	 */
	public boolean hasSegment(Tc2Parser tc2Parser, String spec) {
		boolean returnValue = true;
		try {
			tc2Parser.getMessage().getSegment("/" + spec);
		} catch (HL7Exception he) {
			returnValue = false;
		} 
		return returnValue;
	}
	
	/***
	 * Accepts a message terser and maps the message into a JSON object that represents
	 * values from the file.
	 * @param terser Representation of the message.
	 * 
	 * @return String -- represents the message mapped into a JSON object.
	 */
	public String mapMessage(Tc2Parser tc2Parser) {
		String jsonMapping = "";
		
		try {
			// get the message type you are parsing
			String messageType = tc2Parser.getMessageType();
			String messageTrigger = tc2Parser.getMessageTrigger();
			
			// with the message type, get the right message mapping objects
			JSONArray segmentMapping = (JSONArray) ((JSONObject) this.mapper.get(messageType)).get(messageTrigger);
			Terser terser = tc2Parser.getMessage();
					
			for (Object segmentKey: segmentMapping) {
				/* check that the HashMaps contain segments already */
				this.dataValidationMapping.put((String) segmentKey, new HashMap<String, HashMap<String, String>>());
				
				/* This is the mapping that will be used for the message type*/	
				String[] msgKey = ((String) segmentKey).split("\\-");
				/* test whether the the message contains the segment */			
				if (this.hasSegment(tc2Parser, msgKey[0])) {
					Segment segment = terser.getSegment("/" + msgKey[0]);
					/* with the segment mapping, build a json object that maps values to fields */	
					try {
						String result = this.mapSegmentToValues((JSONObject) this.mapper.get(segmentKey), segment, segmentKey.toString());
					} catch (Exception e) {
						this.logger.error(String.format("Map Message %s", e.getMessage()));
					}
				
				}
			}
			
			
		} catch (HL7Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonMapping;
		
		
	}
	
}
