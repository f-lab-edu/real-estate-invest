package kancho.realestate.comparingprices.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.dto.request.RequestSoaringPriceDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ApartmentPrice;
import kancho.realestate.comparingprices.domain.model.SoaringPrice;
import kancho.realestate.comparingprices.repository.ApartmentPriceRepository;
import kancho.realestate.comparingprices.repository.ApartmentRepository;
import kancho.realestate.comparingprices.repository.SoaringPricesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SoaringPriceService {

	private final SoaringPricesRepository soaringPricesRepository;
	private final ApartmentRepository apartmentRepository;

	// 매일 12시에 새로 상위 10개를 insert
	private static final PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));

	// 금액 상승률이 가장 높은 부동산 정보 가져오기
	public List<SoaringPrice> getSoaringPrices(SoaringPrice.Unit priceDifferenceUnit) {
		return soaringPricesRepository.findSoaringPricesByPriceDifferenceUnit(priceDifferenceUnit.name(),
			pageRequest).getContent();
	}

	public List<SoaringPrice> save(List<RequestSoaringPriceDto> requestSoaringPriceDtoList) {
		// 아파트 객체 리스트 가져오기
		List<Long> apartmentIds = getApartmentIds(requestSoaringPriceDtoList);
		List<Apartment> apartmentList = apartmentRepository.findAllById(apartmentIds);
		// 아파트 객체 리스트와 Dto 리스트로 SoaringPrice 객체 리스트 생성
		List<SoaringPrice> soaringPriceList = getSoaringPrices(apartmentList, requestSoaringPriceDtoList);
		// 저장
		return soaringPricesRepository.saveAll(soaringPriceList);
	}

	private List<Long> getApartmentIds(List<RequestSoaringPriceDto> requestSoaringPriceDtoList) {
		return requestSoaringPriceDtoList.stream()
			.map(dto -> dto.getApartmentId())
			.collect(Collectors.toList());
	}

	private List<SoaringPrice> getSoaringPrices(List<Apartment> apartmentList, List<RequestSoaringPriceDto> requestSoaringPriceDtoList) {
		List<SoaringPrice> soaringPriceList = new ArrayList<>();
		for (int i = 0; i < apartmentList.size(); i++) {
			RequestSoaringPriceDto dto = requestSoaringPriceDtoList.get(i);
			SoaringPrice soaringPrice = new SoaringPrice(apartmentList.get(i), dto.getAreaForExclusiveUse(), dto.getPastDate(), dto.getPastPrice(), dto.getLatestDate(), dto.getLatestPrice(), dto.getPriceDifferenceUnit().toString(), dto.getPriceDifference());
			soaringPriceList.add(soaringPrice);
		}
		return soaringPriceList;
	}

}
