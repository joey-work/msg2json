package qa.asc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.uhn.hl7v2.HL7Exception;
import qa.asc.tc2.Tc2Parser;



public class Tc2ParserTest {
	Tc2Parser tc2Parser;
	
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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		this.tc2Parser = new Tc2Parser(message);
		this.tc2Parser.parseMessage();
		
	}

	@Test
	public void testMessageType() {
		try {
			String  messageType = tc2Parser.getMessageType();
			String  messageTrigger = tc2Parser.getMessageTrigger();
			assertTrue(messageType == "ADT" && messageTrigger == "A01");
		} catch (HL7Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMsgDateTime() throws HL7Exception {
		assertTrue(this.tc2Parser.getMsgDateTime() == "20170912191200");
		
	}
	
	@Test
	public void testSendingFacility() throws HL7Exception {
		assertTrue(this.tc2Parser.getSendingFacility() == "7376");
	}
	
	@Test
	public void testReceivingFacility() throws HL7Exception {
		assertTrue(this.tc2Parser.getReceivingFacility() == "7377");
		
	}
	
	@Test void testMessageControlId() throws HL7Exception {
		assertTrue(this.tc2Parser.getMessageControlId() == "N7377-20180711-19-PR-01");
	}

}
