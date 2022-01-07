package kancho.realestate.utils.excel.storeaprtment.domain;

public enum ApartmentlField {
	REGIONAL_CODE("regional_code",0),
	CITY("city",1),
	GU("gu",2),
	DONG("dong",3),
	JIBUN("jibun",4),
	BONBUN("bonbun",5),
	BUBUN("bubun",6),
	APARTMENT_NAME("apartment_name",7),
	BUILD_YEAR("build_year",8),
	ROAD_ADDRESS("road_address",9);

	private String fieldName;
	private int fieldIndex;

	ApartmentlField(String fieldName, int fieldIndex) {
		this.fieldName = fieldName;
		this.fieldIndex = fieldIndex;
	}

	public String getField() {
		return fieldName;
	}

	public int getIdx() {
		return fieldIndex;
	}
}
