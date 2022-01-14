package kancho.realestate.comparingprices.domain.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum City {
	서울("11");

	private String reginalCode;

	private City(String reginalCode) {
		this.reginalCode = reginalCode;
	}

	public String getReginalCode() {
		return reginalCode;
	}

	public static String getCityName(String reginalCode) {
		final String parsedCode = reginalCode.substring(0,2);
		return Arrays.stream(City.values())
			.filter(city -> city.getReginalCode().equals(parsedCode))
			.collect(Collectors.toList())
			.get(0)
			.toString();
	}
}
