package qa.asc;

import static org.junit.Assert.*;


import org.apache.log4j.Logger;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qa.asc.config.ConfigConstantsEnum;
import qa.asc.tc2.Tc2Mapper;
import qa.asc.tc2.Tc2Parser;


public class Tc2MsgMapperTest {
	Tc2Mapper mapper;
	final static Logger log = Logger.getLogger(Tc2MsgMapperTest.class.getName());
	
	
	/*
	 * 	"PID":{
		""
		
		"PID": { "section":"patients:registration:demographics",  			
		    "5.1": {"pep": "lastName", "uiid": "PD_NAME_FIELD", "ui":"Last Name", "db": "", "csv": ""},
			"5.2": {"pep": "firstName", "uiid": "PD_NAME_FIELD", "uiid": "", "ui":"First Name", "db": "", "csv": ""},
			"5.3": {"pep": "Middle Name", "uiid": "", "ui":"", "db": "", "csv": ""},
			"4.1": {"pep": "fmp", "uiid": "", "ui":"FMP", "db": ""},
			"4.2": {"pep": "sponsorSsn", "uiid": "", "ui":"Sponsor SSN", "db": "", "csv": ""},
			"7": {"pep": "dob", "uiid": "", "ui":"DOB", "db": "", "csv": ""},
			"8": {"pep": "gender", "uiid": "", "ui":"Gender", "db": "", "csv": ""},
			"10": {"pep": "race", "uiid": "PD_RACE_FIELD", "ui":"Race", "db": "", "csv": ""},
			"19": {"pep": "ssn", "uiid": "PD_SSN_FIELD", "ui":"SSN", "db": "", "csv": ""},
			"27": {"pep": "branch", "uiid": "", "ui":"Branch", "db": "", "csv": ""},
			"27.4": {"pep": "patientCategory", "uiid": "", "ui":"Patient Category", "db": "", "csv": ""}
  }
		
		
		"19": {"pep": "ssn", "uiid": "PD_SSN_FIELD", "ui":"SSN", "db": "", "csv": ""}
		}


		"patientSummary": {
			  	"PATIENT_DEMOGRAPHICS_TAB": {
			      "PD_NAME_FIELD": "Jones", "firstName": "Andrew", 
			      "PD_NATION_FIELD": "",
			      "PD_RACE_FIELD": "",
				  "PD_SSN_FIELD": "936-42-4977", 
				  "PD_GENDER_FIELD": "Male", 
				   "PD_UNIT_EMPLOYER_FIELD": "",   
			      "PD_FMP_SPONSOR_SSN_FIELD": "20/936-42-4977",  
			      "age": "", 
			      "PD_RANK_FIELD": "",
			      "PD_DOB_FIELD": "01/01/1978", 
			      "patientCategory": "A11 - ARMY ACTIVE DUTY",
				  "latestDiagnosis": "",
				  "PD_DODID_FIELD": "",
				  "sf502": ""
				  }


	 */
	
	
	String message = "MSH^\\|~&^CHCS\\ADT^7376^^7377^20170912191200^^ADT\\A01^N7377-20180711-19-PR-01^P^2.2^2018070319\r" + 
			"EVN^A01^20170912191200\r" + 
			"PID^1^^20180703\\\\\\HP7377^20/936-42-4977^SANTOS\\CHRISTOPHER^^19950312000000^M^^2047-9^30604 SAN CARLOS ST\\\\MONROEVILLE\\NORTH DAKOTA\\44847^US^(304)756-9876^(304)756-9876^^M^1034^^936-42-4977^^^3^^^^^4106\\USA ACTIVE DUTY OFFICER\\99PAT\\A11\\\\99PTC\r" + 
			"NK1^1^MARIE SANTOS ROBINSON^1\\SISTER OF PATIENT\\99FAM^605 LIPOA PARKWAY\\\\KIHEI\\HI\\96753^(808)213-3002\r" + 
			"PV1^1^I^Role 2 0758 FST (Mosul, IRQ)\\\\WC47AA\\A7437\\A7437^4^^^4\\LM SORENSON\\MD\\\\\\\\\\99STF^^^561\\AAIA\\99ME^^^^561\\AAIA\\99MEP^^^9\\DG CROCKER\\MD\\\\\\\\\\99STF^USA ACTIVE DUTY OFFICER\\99PAT\\99PAT^1.1^^^^^^^^^^^^^^^^^36\\PATIENT ADMITTED\\99DIS^^^^^^^^20170912191200\r" + 
			"DG1^1^I9^862.9^Injury to multiple and unspecified intrathoracic organs, with open wound into cavity\\99IC9\r" + 
			"DG1^2^I9^875.1^Open wound of chest (wall), complicated\\99IC9\r" + 
			"DG1^3^99^^Penetrating wounds to the chest, arm, and abdomen.\r" + 
			"PR1^1^I9^00.01^THERAPEUTIC ULTRASOUND OF VESSELS OF HEAD AND NECK\r" + 
			"PR1^2^I9^35.95^REVISION OF CORRECTIVE PROCEDURE ON HEART\r" + 
			"ZP1^^^193\\1LT\\99RNK^^5^9\\DG CROCKER\\MD\\\\\\\\\\99STF^PATIENT IS ADMITTED TO ICU^^^20170416^Y\r" + 
			"ZPX^^^^^7377\\Role 2 0758 FST (Mosul, IRQ)\\99DMI";
	
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
			"	},\n" + 
			"\"ADT\": {\"A01\":[\"PID\",\"MSH\", \"EVN\",  \"PV1\", \"NK1\", \"AL1\", \"DG1\", \"PR1\", \"ZP1\", \"ZPX\"]},\n" +
			"\"PID\": { \"section\":\"patients:registration:demographics\","+
			"  			\"optional\":\"false\", \"repeat\":\"false\",\n"+
			"		    \"5-1\": {\"pep\": \"lastName\", \"uiid\": \"PD_NAME_FIELD\", \"ui\":\"Last Name\", \"db\": \"\", \"csv\": \"\"},\n"+
			"			\"5-2\": {\"pep\": \"firstName\", \"uiid\": \"PD_NAME_FIELD\", \"ui\":\"First Name\", \"db\": \"\", \"csv\": \"\"},\n"+
			"			\"5-3\": {\"pep\": \"Middle Name\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n" +
			"			\"4-1\": {\"pep\": \"fmp\", \"uiid\": \"PD_FMP_SPONSOR_SSN_FIELD\", \"ui\":\"FMP\", \"db\": \"\"},\n" +
			"			\"4-2\": {\"pep\": \"sponsorSsn\", \"uiid\": \"PD_SPONSOR_SSN\", \"ui\":\"Sponsor SSN\", \"db\": \"\", \"csv\": \"\"},\n" +
			"			\"7\": {\"pep\": \"dob\", \"uiid\": \"PD_DOB\", \"ui\":\"DOB\", \"db\": \"\", \"csv\": \"\"},\n" +
			"			\"8\": {\"pep\": \"gender\", \"uiid\": \"PD_GENDER_FIELD\", \"ui\":\"Gender\", \"db\": \"\", \"csv\": \"\"},\n" +
			"			\"10\": {\"pep\": \"race\", \"uiid\": \"PD_RACE_FIELD\", \"ui\":\"Race\", \"db\": \"\", \"csv\": \"\"},\n" +
			"			\"19\": {\"pep\": \"ssn\", \"uiid\": \"PD_SSN_FIELD\", \"ui\":\"SSN\", \"db\": \"\", \"csv\": \"\"},\n" +
			"			\"27\": {\"pep\": \"branch\", \"uiid\": \"PD_BRANCH\", \"ui\":\"Branch\", \"db\": \"\", \"csv\": \"\"},\n" +
			"			\"7-4\": {\"pep\": \"patientCategory\", \"uiid\": \"\", \"ui\":\"Patient Category\", \"db\": \"\", \"csv\": \"\"}\n" +
			"  },\n"+
			"  \"DG1\": { \"section\": \"patients:summary:medicalEventsHistory:inpatient\",\n"+
			"  			\"optional\":\"true\", \"repeat\":\"true\",\n"+
			"  			\"1\": {\"pep\": \"setId\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  			\"2\": {\"pep\": \"codingMethod\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  			\"3\": {\"pep\": \"diagnosisCode\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  			\"4\": {\"pep\": \"diagnosisText\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  			\"5\": {\"pep\": \"diagnosisDateTime\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  			\"6\": {\"pep\": \"diagnosisType\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  			\"15\": {\"pep\": \"diagnosisPriority\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"}\n"+
			"  },\n"+
			"  \"EVN\": { \"section\": \"message\",\n"+
			"  			\"optional\":\"false\", \"repeat\":\"false,\"\n"+
			"  	\"1\": {\"pep\": \"eventTypeCode\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  	\"2\": {\"pep\": \"eventDateTime\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  	\"3\": {\"pep\": \"planEventDateTime\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"  	\"4\": {\"pep\": \"eventReasonCode\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"}\n"+
			"  },\n"+
			"\"MSH\": {  \"section\" : \"message\",\n"+
			"  		 \"optional\":\"false\", \"repeat\":\"false,\"\n"+
			"        \"1\": {\"pep\": \"fieldSeparator\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"2\": {\"pep\": \"orderCodeReason\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"3\": {\"pep\": \"sendingApplication\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"4\": {\"pep\": \"sendingFacility\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"5\": {\"pep\": \"receivingApplication\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"6\": {\"pep\": \"receivingFacility\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"7\": {\"pep\": \"dateTimeMessage\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"9-1\": {\"pep\": \"messageType\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"9-2\": {\"pep\": \"trigger\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"10\": {\"pep\": \"messageControlId\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"11\": {\"pep\": \"processingId\", \"uiid\": \"PROCESSING_ID\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"12\": {\"pep\": \"versionId\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"13\": {\"pep\": \"sequenceNumber\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"17\": {\"pep\": \"countryCode\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"}\n"+
			"  },\n"+
			"  \"NK1\": {\n"+
			"  		 \"optional\":\"true\", \"repeat\":\"false,\"\n"+
			"        \"section\" : \"message\",\n"+
			"        \"1\": {\"pep\": \"setId\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"2\": {\"pep\": \"emergencyContactName\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"3\": {\"pep\": \"emergencyContactRelationship\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"4\": {\"pep\": \"emergencyContactAddress\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"5\": {\"pep\": \"emergencyContactPhone\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"7\": {\"pep\": \"emergencyContactRole\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"}\n"+
			"  },\n"+
			"  \"PV1\": { \"sections\": \"\",\n"+
			"  		 \"optional\":\"true\", \"repeat\":\"false,\"\n"+
			"        \"1\": {\"pep\": \"setID\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"2\": {\"pep\": \"patientClass\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"3\": {\"pep\": \"assignedLocation\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"4\": {\"pep\": \"admissionType\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"5\": {\"pep\": \"preAdmitNumber\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"6\": {\"pep\": \"priorLocation\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"7\": {\"pep\": \"attending\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"10\": {\"pep\": \"hospService\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"11\": {\"pep\": \"tempLocation\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"12\": {\"pep\": \"admitSource\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"17\": {\"pep\": \"admitDoctor\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"18\": {\"pep\": \"patientType\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"19\": {\"pep\": \"visitNumber\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"20\": {\"pep\": \"financialClass\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"36\": {\"pep\": \"dischDisposition\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"37\": {\"pep\": \"dischLocation\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"44\": {\"pep\": \"admitDateTime\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"45\": {\"pep\": \"dischDateTime\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"}\n"+
			"  },\n"+
			"  \"ZP1\": { \"sections\": \"\",\n"+
			"  		 \"optional\":\"true\", \"repeat\":\"false,\"\n"+
			"        \"1\": {\"pep\": \"patientOccupation\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"2\": {\"pep\": \"outpatientRecLoc\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"3-1\": {\"pep\": \"payGrade\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"3-2\": {\"pep\": \"rank\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"4-1\": {\"pep\": \"cicCode\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"4-2\": {\"pep\": \"cicDescription\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"5\": {\"pep\": \"cisMilitaryStatus\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"6\": {\"pep\": \"cisPrimaryPhysician\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"7\": {\"pep\": \"admissionRemarks\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"8\": {\"pep\": \"casualtyStatus\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"9\": {\"pep\": \"casualtyDate\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"10\": {\"pep\": \"organDonorDate\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"11\": {\"pep\": \"organDonorFlag\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"        \"12\": {\"pep\": \"flyingStatus\", \"uiid\": \"\", \"ui\":\"\", \"db\": \"\", \"csv\": \"\"},\n"+
			"       }\n"+
			"}";
	
Tc2Parser tc2Parser;
	


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.info("Starting Test Tc2MsgMapperTest");
	}

	@Before
	public void setUp() throws Exception {
		this.tc2Parser = new Tc2Parser(message);
		this.tc2Parser.parseMessage();
		this.mapper = new Tc2Mapper(log);
	}

	@Test
	public void test() {
		this.mapper.setTc2MapperObject(this.jsonString);
		JSONObject o = (JSONObject) this.mapper.get(ConfigConstantsEnum.COMMON.value());
		this.mapper.mapMessage(this.tc2Parser);
	}

}
