/**
 * 
 */
package qa.asc.config;

/**
 * @author rob
 *
 */
public enum ConfigConstantsEnum {
	TC2_OBJECT("tc2"),
	PATH("path"),
	AHLTAT_OBJECT("ahltat"),
	MCC_OBJECT("mcc"),
	SAMS8_OBJECT("sams8"),
	FILE_MASK("file_mask"),
	MAPPINGS("mappings"),
	MAPPING_FILES("mapping_files"),
	COMMON("Common"),
	MAPPING_FILE_NAME("mapping_file_name"),
	MSH("MSH"),
	PID("PID"),
	EVN("EVN"),
	ADT("ADT"),
	A01("A01"),
	SECTION("section"),
	UI("ui"),
	UIID("uiid"),
	PEP("pep"),
	DB("db");
	
	

    private final String value;
	
	ConfigConstantsEnum(String string) {
		// TODO Auto-generated constructor stub
		this.value = string;
	}
	
	public String value() {return this.value;}
	
	public String toString() {return this.value();}

	

}
