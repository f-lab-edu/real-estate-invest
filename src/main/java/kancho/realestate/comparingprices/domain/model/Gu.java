package kancho.realestate.comparingprices.domain.model;

public enum Gu {
	강남구("11680"), 강동구("11740"), 강북구("11305"), 강서구("11500"),
	관악구("11620"), 광진구("11215"), 구로구("11530"), 금천구("11545"),
	노원구("11350"), 도봉구("11320"), 동대문구("11230"), 동작구("11590"),
	마포구("11440"), 서대문구("11410"), 서초구("11650"), 성동구("11200"),
	성북구("11290"), 송파구("11710"), 양천구("11470"), 영등포구("11560"),
	용산구("11170"), 은평구("11380"), 종로구("11110"), 중구("11140"),
	중랑구("11260");

	private String regionalCode;

	private Gu(String regionalCode) {
		this.regionalCode = regionalCode;
	}

	public String getReginalCode() {
		return regionalCode;
	}

	public static boolean isCorrectRegionalCode(String regionalCode) {
		for (Gu gu : Gu.values()) {
			if (gu.getReginalCode().equals(regionalCode)) {
				return true;
			}
		}
		return false;
	}
}