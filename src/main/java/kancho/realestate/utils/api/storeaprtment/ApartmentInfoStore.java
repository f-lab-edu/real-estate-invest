package kancho.realestate.utils.api.storeaprtment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDetailDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.vo.ApartmentDetail;
import kancho.realestate.comparingprices.domain.model.ApartmentPrice;
import kancho.realestate.comparingprices.domain.vo.ApartmentPriceUniqueInfo;
import kancho.realestate.comparingprices.domain.vo.ApartmentUniqueInfo;
import kancho.realestate.comparingprices.domain.vo.Gu;
import kancho.realestate.utils.api.storeaprtment.service.ApartmentApiService;
import lombok.RequiredArgsConstructor;

@Profile("!test") // 테스트시 실행 x
@RequiredArgsConstructor
@Component
public class ApartmentInfoStore implements ApplicationRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ApartmentApiClient apartmentApiClient;
	private final ApartmentApiService apartmentApiService;

	private final int numOfMonth = 1; // 데이터로 가져올 개월 수

	Map<ApartmentUniqueInfo, Long> existingApartmentData;
	Map<ApartmentPriceUniqueInfo, Long> existingApartmentPriceData;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 데이터베이스에 저장된 기존 아파트, 아파트 가격 정보 받아오기
		existingApartmentData = getExistingApartments();
		existingApartmentPriceData = getExistingApartmentPrices();
		// 과거 부터 현재까지 모든 아파트 정보 데이터 요청하여 저장하기
		saveAllOfApartmentInfo();
	}

	private void saveAllOfApartmentInfo() throws JAXBException, IOException, InterruptedException {
		// 오늘 날짜 정보 가져오기
		LocalDateTime now = LocalDateTime.now();
		// 각 월 마다 거래된 구별 아파트 정보 가져오기
		for (int minusMonths = 0; minusMonths < numOfMonth; minusMonths++) {
			for (Gu gu : Gu.values()) {
				int dealYear = now.minusMonths(numOfMonth).getYear();
				int dealMonth = now.minusMonths(numOfMonth).getMonthValue();
				List<ApartmentDetail> details = apartmentApiClient.
					getApartmentDetails(new RequestApartmentDetailDto(gu.getReginalCode(), dealYear, dealMonth)); // 공공 데이터 api로 데이터를 받아서
				saveApartmentDetails(details); // 데이터베이스에 저장
				Thread.sleep(2000);
			}
		}
	}

	private void saveApartmentDetails(List<ApartmentDetail> details) {
		for (ApartmentDetail apartmentDetail : details) {
			// 아파트 정보 저장
			long apartmentId = saveApartment(apartmentDetail);
			apartmentDetail.setApartmentId(apartmentId);
			// 아파트 가격 정보 저장
			saveApartmentPrice(apartmentDetail);
		}
	}

	private Map<ApartmentUniqueInfo, Long> getExistingApartments() {
		return apartmentApiService.findAllApartments().stream() // 기존 아파트 정보를 찾아서,
			.collect(Collectors.toMap(Apartment::getApartmentUniqueInfo, Apartment::getId)); // Map 자료구조로 매핑
	}

	private Map<ApartmentPriceUniqueInfo, Long> getExistingApartmentPrices() {
		return apartmentApiService.findAllApartmentsPrice().stream() // 기존 아파트 가격 정보를 찾아서,
			.collect(
				Collectors.toMap(ApartmentPrice::getApartmentPriceUniqueInfo, ApartmentPrice::getId)); // Map 자료구조로 매핑
	}


	private long saveApartment(ApartmentDetail apartmentDetail) {
		long apartmentId = 0L;
		Apartment apartment = apartmentDetail.getApartment();

		if (isNew(existingApartmentData, apartment)) {
			apartmentId = apartmentApiService.save(apartment).getId();
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
			long id = apartmentApiService.save(apartmentPrice).getApartmentId();
			existingApartmentPriceData.put(new ApartmentPriceUniqueInfo(apartmentPrice), id);
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
