package kancho.realestate.comparingprices.scheduler.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDetailDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.vo.ApartmentDetail;
import kancho.realestate.comparingprices.domain.model.ApartmentPrice;
import kancho.realestate.comparingprices.domain.vo.ApartmentPriceUniqueInfo;
import kancho.realestate.comparingprices.domain.vo.ApartmentUniqueInfo;
import kancho.realestate.comparingprices.domain.vo.Gu;
import kancho.realestate.comparingprices.exception.InvalidApartmentXmlException;
import kancho.realestate.comparingprices.service.ApartmentService;

@Profile("!test") // 테스트시 실행 x
@Component
public class ApartmentStorer  {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ApartmentApiClient apartmentApiClient;
	private final ApartmentService apartmentService;

	private Map<ApartmentUniqueInfo, Long> existingApartmentData;
	private Map<ApartmentPriceUniqueInfo, Long> existingApartmentPriceData;

	public ApartmentStorer(ApartmentApiClient apartmentApiClient, ApartmentService apartmentService) {
		this.apartmentApiClient = apartmentApiClient;
		this.apartmentService = apartmentService;
		// 데이터베이스에 저장된 기존 아파트, 아파트 가격 정보 받아오기
		existingApartmentData = getExistingApartments();
		existingApartmentPriceData = getExistingApartmentPrices();
	}

	/*
	 * @param numOfMonth: 데이터를 가져올 개월수
	 */
	public void saveAllOfApartmentInfo(int numOfMonth) throws JAXBException, IOException, InterruptedException {
		// 오늘 날짜 정보 가져오기
		LocalDateTime now = LocalDateTime.now();
		// 각 월 마다 거래된 구별 아파트 정보 가져오기
		for (int minusMonths = numOfMonth - 1; minusMonths >= 0; minusMonths--) {
			for (Gu gu : Gu.values()) {
				int dealYear = now.minusMonths(minusMonths).getYear();
				int dealMonth = now.minusMonths(minusMonths).getMonthValue();
				try {
					List<ApartmentDetail> details = apartmentApiClient.
						getApartmentDetails(new RequestApartmentDetailDto(gu.getReginalCode(), dealYear, dealMonth)); // 공공 데이터 api로 데이터를 받아서
					saveApartmentDetails(details); // 데이터베이스에 저장
					Thread.sleep(2000);
				} catch (InvalidApartmentXmlException e) {
					logger.info("아파트 정보 파싱 실패 = {}", e);
				}
			}
		}
	}

	private void saveApartmentDetails(List<ApartmentDetail> details) {
		for (ApartmentDetail apartmentDetail : details) {
			if (apartmentDetail.isCorrectData()){
				// 아파트 정보 저장
				long apartmentId = saveApartment(apartmentDetail);
				apartmentDetail.setApartmentId(apartmentId);
				// 아파트 가격 정보 저장
				saveApartmentPrice(apartmentDetail);
			}
		}
	}

	private Map<ApartmentUniqueInfo, Long> getExistingApartments() {
		return apartmentService.findAllApartments().stream() // 기존 아파트 정보를 찾아서,
			.collect(Collectors.toMap(Apartment::getApartmentUniqueInfo, Apartment::getId)); // Map 자료구조로 매핑
	}

	private Map<ApartmentPriceUniqueInfo, Long> getExistingApartmentPrices() {
		return apartmentService.findAllApartmentsPrice().stream() // 기존 아파트 가격 정보를 찾아서,
			.collect(
				Collectors.toMap(ApartmentPrice::getApartmentPriceUniqueInfo, ApartmentPrice::getId)); // Map 자료구조로 매핑
	}


	private long saveApartment(ApartmentDetail apartmentDetail) {
		long apartmentId = 0L;
		Apartment apartment = apartmentDetail.getApartment();

		if (isNew(existingApartmentData, apartment)) {
			Apartment saved = apartmentService.save(apartment);
			apartmentId = saved.getId();
			existingApartmentData.put(new ApartmentUniqueInfo(apartment), apartmentId);
			logger.info("아파트 정보 저장 성공 = {}", apartment);
		} else {
			apartmentId = existingApartmentData.get(new ApartmentUniqueInfo(apartment));
			logger.info("아파트 정보 저장 실패, 중복된 데이터 = {}", apartment);
		}
		return apartmentId;
	}

	private void saveApartmentPrice(ApartmentDetail apartmentDetail) {
		ApartmentPrice apartmentPrice = apartmentDetail.getApartmentPrice();

		if (isNew(existingApartmentPriceData, apartmentPrice)) {
			ApartmentPrice saved = apartmentService.save(apartmentPrice);
			existingApartmentPriceData.put(new ApartmentPriceUniqueInfo(apartmentPrice), saved.getId());
			logger.info("아파트 가격 정보 저장 성공 = {}", apartmentPrice);
		} else {
			logger.info("아파트 가격 정보 저장 실패, 중복된 데이터 = {}", apartmentPrice);
		}
	}

	private boolean isNew(Map<ApartmentUniqueInfo, Long> existingData, Apartment apartment) {
		return !existingData.containsKey(new ApartmentUniqueInfo(apartment));
	}

	private boolean isNew(Map<ApartmentPriceUniqueInfo, Long> existingData, ApartmentPrice apartmentPrice) {
		return !existingData.containsKey(new ApartmentPriceUniqueInfo(apartmentPrice));
	}
}
