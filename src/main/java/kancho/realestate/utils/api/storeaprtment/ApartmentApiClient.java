package kancho.realestate.utils.api.storeaprtment;

import static java.net.URLEncoder.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDetailDto;
import kancho.realestate.comparingprices.domain.model.ApartmentDetail;
import kancho.realestate.utils.api.storeaprtment.mapper.ApartmentXmlMapper;

@Component
public class ApartmentApiClient {
	@Value("${data-go-kr.apartment-price-detail.accessKey}")
	private String accessKey;
	@Value("${data-go-kr.domain-name}")
	private String domainName;
	@Value("${data-go-kr.apartment-price-detail.path}")
	private String path;

	private final String encoder = "UTF-8";
	private final String pageNo = "1"; // 페이지번호
	private final String numOfRows = "1000"; // 한 페이지 결과 수

	public List<ApartmentDetail> getApartmentDetails(RequestApartmentDetailDto requestDto) throws IOException, JAXBException {
			URL url = new URL(makeRequestUrl(requestDto));
			String responseAsXmlString = request(url); // 공공 API에 데이터 요청
			return new ApartmentXmlMapper().apartmentXmlToList(responseAsXmlString);
	}

	private String makeRequestUrl(RequestApartmentDetailDto requestDto) throws UnsupportedEncodingException {
		String dealYearAndMonth = Integer.toString(requestDto.getDealYear()) + Integer.toString(requestDto.getDealMonth());
		StringBuilder urlBuilder = new StringBuilder();
		return urlBuilder.append(domainName).append(path)
			.append("?").append(encode("serviceKey", encoder)).append(accessKey)
			.append("&").append(encode("pageNo", encoder)).append("=").append(encode(pageNo, encoder))
			.append("&").append(encode("numOfRows", encoder)).append("=").append(encode(numOfRows, encoder))
			.append("&").append(encode("LAWD_CD", encoder)).append("=").append(encode(requestDto.getRegionalCode(), encoder))
			.append("&").append(encode("DEAL_YMD", encoder)).append("=").append(encode(dealYearAndMonth, encoder))
			.toString();
	}

	private String request(URL url) throws IOException {
		HttpURLConnection httpURLConnection = getHttpURLConnection(url);
		String response = getHttpResponse(httpURLConnection);
		httpURLConnection.disconnect();
		return response;
	}

	private HttpURLConnection getHttpURLConnection(URL url) throws IOException {
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty("Content-type", "application/json");
		return httpURLConnection;
	}

	private String getHttpResponse(HttpURLConnection httpURLConnection) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
		}
		rd.close();

		return response.toString();
	}
}