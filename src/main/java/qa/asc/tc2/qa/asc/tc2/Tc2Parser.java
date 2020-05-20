/**
 * 
 */
package qa.asc.tc2;


import java.io.IOException;
import java.util.ArrayList;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.v22.segment.PID;
import ca.uhn.hl7v2.parser.*;
import ca.uhn.hl7v2.util.Terser;
import qa.asc.tc2.Tc2Mapper;

/**
 * @author rob
 *
 */
public class Tc2Parser {
	String message;
	Terser terser;
	Tc2Mapper tc2Mapper;
	
	
	public Tc2Parser(String message) {
		this.message = message;
		
	}
	
	/*
	 * Loads the mapping file for enumerating data.
	 */
	public void setMessageMapping(Tc2Mapper tc2Mapper){
		this.tc2Mapper = tc2Mapper;
		
	}
	
	public void parseMessage() throws HL7Exception, IOException {
		 HapiContext context = new DefaultHapiContext();
		 Parser p = context.getGenericParser();
		 Message hapiMsg = p.parse(this.message);
		 this.terser = new Terser(hapiMsg);
		 context.close();
	}
	
	public String getMessageType() throws HL7Exception {
		return this.terser.get("/.MSH-9-1");
	}
	
	public String getMessageTrigger()  throws HL7Exception {
		return this.terser.get("/.MSH-9-2");
	}
	
	public String getMessageControlId() throws HL7Exception {
		return this.terser.get("/MSH-10");
	}

	public String getMsgDateTime() throws HL7Exception {
		return this.terser.get("/MSH-7");
	}
	
	public Terser getMessage() {
		return this.terser;
	}
	
	public String getSendingFacility() throws HL7Exception {
		return this.terser.get("/MSH-4");
		
	}
	
	public String getReceivingFacility() throws HL7Exception {
		return this.terser.get("/MSH-6");
		
	}
}
