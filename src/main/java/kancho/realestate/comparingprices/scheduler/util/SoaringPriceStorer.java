package kancho.realestate.comparingprices.scheduler.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.domain.dto.request.RequestSoaringPriceDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentPriceDto;
import kancho.realestate.comparingprices.domain.model.SoaringPrice;
import kancho.realestate.comparingprices.service.ApartmentService;
import kancho.realestate.comparingprices.service.SoaringPriceService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SoaringPriceStorer {
	private final ApartmentService apartmentService;
	private final SoaringPriceService soaringPriceService;

	public List<ResponseApartmentPriceDto> getApartmenstPriceFromLastyear() {
		int end = LocalDateTime.now().getYear();
		int start = end - 1;
		return apartmentService.findApartmentPriceByDealYearBetween(start, end);
	}

	public Map<String, ResponseApartmentPriceDto> getLatestApartmentPrices(
		List<ResponseApartmentPriceDto> apartmentPriceList) {
		var sortedApartmentPriceList = getSortedByDateApartmentPriceList(apartmentPriceList);
		var latestPriceMap = new HashMap<String, ResponseApartmentPriceDto>();
		for (ResponseApartmentPriceDto apartmentPrice : sortedApartmentPriceList) {
			latestPriceMap.put(makeApartmentUniqueId(apartmentPrice), apartmentPrice);
		}
		return latestPriceMap;
	}

	// 계약일의 역순으로 정렬
	private List<ResponseApartmentPriceDto> getSortedByDateApartmentPriceList(
		List<ResponseApartmentPriceDto> apartmentPriceList) {
		apartmentPriceList.sort((a,b) -> {
			LocalDate localDateA = LocalDate.of(a.getDealYear(), a.getDealMonth(), a.getDealDay());
			LocalDate localDateB = LocalDate.of(b.getDealYear(), b.getDealMonth(), b.getDealDay());
			return localDateA.compareTo(localDateB);
		});
		return apartmentPriceList;
	}

	public Map<String, ResponseApartmentPriceDto> getCheapestPriceListFromLastYear(
		List<ResponseApartmentPriceDto> apartmentPriceList) {
		var cheapestPriceMap = new HashMap<String, ResponseApartmentPriceDto>();
		int lastYear = LocalDateTime.now().getYear() - 1;
		for (ResponseApartmentPriceDto apartmentPrice : apartmentPriceList) {
			// 작년에 거래되었고,
			if (apartmentPrice.getDealYear() != lastYear)
				continue;
			// 가장 낮은 금액으로 거래된 매물만 가져오기
			String id = makeApartmentUniqueId(apartmentPrice);
			if (cheapestPriceMap.get(id) == null) {
				cheapestPriceMap.put(id, apartmentPrice);
			} else {
				ResponseApartmentPriceDto saved = cheapestPriceMap.get(id);
				if (saved.getDealAmount() > apartmentPrice.getDealAmount()) {
					cheapestPriceMap.put(id, apartmentPrice);
				}
			}
		}
		return cheapestPriceMap;
	}

	public List<RequestSoaringPriceDto> getSoaringPriceList(Map<String, ResponseApartmentPriceDto> latestPriceMap,
		Map<String, ResponseApartmentPriceDto> cheapestPriceMap, SoaringPrice.Unit soaringPriceUnit) {
		var soaringPriceList = makeSoaringPriceList(latestPriceMap, cheapestPriceMap, soaringPriceUnit);
		return filterTopLevel(soaringPriceList); // 급상승 가격정보 중 최상위 10개만 추출하여 반환한다.
	}

	public List<SoaringPrice> saveSoaringPriceList(List<RequestSoaringPriceDto> soaringPriceByWonList, List<RequestSoaringPriceDto> soaringPriceByPercentList) {
		List<SoaringPrice> savedWonList = soaringPriceService.save(soaringPriceByWonList);
		List<SoaringPrice> savedPercentList = soaringPriceService.save(soaringPriceByPercentList);
		savedWonList.addAll(savedPercentList);
		return savedWonList;
	}

	private String makeApartmentUniqueId(ResponseApartmentPriceDto apartmentPrice) {
		StringBuilder sb = new StringBuilder();
		return sb.append(apartmentPrice.getApartmentId())
			.append("-")
			.append(apartmentPrice.getAreaForExclusiveUse())
			.toString();
	}

	private List<RequestSoaringPriceDto> filterTopLevel(List<RequestSoaringPriceDto> soaringPriceList) {
		soaringPriceList.sort((a,b)->(int)(b.getPriceDifference() - a.getPriceDifference()));
		if (soaringPriceList.size() > 10) {
			return soaringPriceList.subList(0, 10);
		}
		return soaringPriceList;
	}

	private List<RequestSoaringPriceDto> makeSoaringPriceList(Map<String, ResponseApartmentPriceDto> latestPriceMap,
		Map<String, ResponseApartmentPriceDto> cheapestPriceMap, SoaringPrice.Unit soaringPriceUnit) {
		var soaringPriceList = new ArrayList<RequestSoaringPriceDto>();
		for (Map.Entry<String, ResponseApartmentPriceDto> latestPriceEntry : latestPriceMap.entrySet()) {
			String id = latestPriceEntry.getKey();
			ResponseApartmentPriceDto latestPrice = latestPriceEntry.getValue();
			if (cheapestPriceMap.containsKey(id)) {
				ResponseApartmentPriceDto cheapestPrice = cheapestPriceMap.get(id);
				soaringPriceList.add(makeSoaringPrice(latestPrice, cheapestPrice, soaringPriceUnit));
			}
		}
		return soaringPriceList;
	}

	private RequestSoaringPriceDto makeSoaringPrice(ResponseApartmentPriceDto latestPrice,
		ResponseApartmentPriceDto pastPrice, SoaringPrice.Unit unit) {
		return new RequestSoaringPriceDto(
			pastPrice.getApartmentId(), pastPrice.getAreaForExclusiveUse().toString(),
			LocalDate.of(pastPrice.getDealYear(), pastPrice.getDealMonth(), pastPrice.getDealDay()),
			pastPrice.getDealAmount(),
			LocalDate.of(latestPrice.getDealYear(), latestPrice.getDealMonth(), latestPrice.getDealDay()),
			latestPrice.getDealAmount(),
			unit, unit.calculatePriceDifference(latestPrice.getDealAmount(), pastPrice.getDealAmount())
		);
	}
}
