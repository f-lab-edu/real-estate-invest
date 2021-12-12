package kancho.realestate.utils.api.storeaprtment;

import static java.net.URLEncoder.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.domain.dto.ApartmentDetail;
import kancho.realestate.comparingprices.domain.dto.ApartmentDetails;

@Component
public class ApartmentApiClient {
	@Value("${data-go-kr.apartment-price-detail.accessKey}")
	private String accessKey; // 인증키

	private final String path = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"; // 요청 경로
	private final String encoder = "UTF-8";
	private final String pageNo = "1"; // 페이지번호
	private final String numOfRows = "1000"; // 한 페이지 결과 수

	/*
	 * @Param lawdCd 지역코드
	 * @Param dealYMD 계약월
	 */
	public List<ApartmentDetail> getApartmentDetails(String lawdCd, String dealYMD) throws IOException, JAXBException {
		// 요청 url 생성
		URL url = new URL(makeRequestUrlString(lawdCd,dealYMD));
		// 공공 API에 데이터 요청
		String raw = request(url);
		// 가져온 데이터를 객체로 변환
		return xmlToList(raw);
	}

	private String makeRequestUrlString(String lawdCd, String dealYMD) throws UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder(path);
		urlBuilder.append("?" + encode("serviceKey", encoder) + accessKey);
		urlBuilder.append(
			"&" + encode("pageNo", encoder) + "=" + encode(pageNo, encoder));
		urlBuilder.append(
			"&" + encode("numOfRows", encoder) + "=" + encode(numOfRows, encoder));
		urlBuilder.append(
			"&" + encode("LAWD_CD", encoder) + "=" + encode(lawdCd, encoder));
		urlBuilder.append(
			"&" + encode("DEAL_YMD", encoder) + "=" + encode(dealYMD, encoder));
		return urlBuilder.toString();
	}

	private String request(URL url) throws IOException {
		// 요청
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("code:"+conn.getResponseCode());

		// 받아온 데이터 읽기
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		// Stream to String
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		// 요청 닫기
		rd.close();
		conn.disconnect();

		String raw = sb.toString();

		return sb.toString();
	}

	// 문자열 xml 데이터를 List 객체 형식으로 변환
	private List<ApartmentDetail> xmlToList(String xmlString) throws JAXBException {
		// 객체 파싱을 위해 필요한 부분만 추출
		String start = "<items>";
		String end = "</items>";
		xmlString = xmlString.substring(xmlString.indexOf(start), xmlString.indexOf(end) + end.length());

		// JAXB를 통한 xml to Object 변환
		JAXBContext jaxbContext = JAXBContext.newInstance(ApartmentDetails.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		ApartmentDetails apartmentDetails = (ApartmentDetails)unmarshaller
			.unmarshal(new StringReader(xmlString));

		return apartmentDetails.getApartmentDetailList();
	}
}
