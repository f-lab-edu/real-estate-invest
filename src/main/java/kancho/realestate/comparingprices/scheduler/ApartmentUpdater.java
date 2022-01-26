package kancho.realestate.comparingprices.scheduler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.domain.dto.request.RequestSoaringPriceDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentPriceDto;
import kancho.realestate.comparingprices.domain.model.SoaringPrice;
import kancho.realestate.comparingprices.scheduler.util.ApartmentStorer;
import kancho.realestate.comparingprices.scheduler.util.SoaringPriceStorer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ApartmentUpdater {

	private final ApartmentStorer apartmentStorer;
	private final SoaringPriceStorer soaringPriceStorer;

	// 매일 밤 12시 마다 실행
	@Scheduled(cron = "0 0 0 * * *")
	public void update() throws JAXBException, IOException, InterruptedException {
		updateApartmentInfos();
		updateSoaringPrices();
	}

	private void updateApartmentInfos() throws JAXBException, IOException, InterruptedException {
		apartmentStorer.saveAllOfApartmentInfo(1);
	}

	private void updateSoaringPrices() {
		List<ResponseApartmentPriceDto> apartmentPriceList = soaringPriceStorer.getApartmenstPriceFromLastyear();
		Map<String, ResponseApartmentPriceDto> latestPriceMap = soaringPriceStorer.getLatestApartmentPrices(apartmentPriceList);
		Map<String, ResponseApartmentPriceDto> cheapestPriceMap = soaringPriceStorer.getCheapestPriceListFromLastYear(apartmentPriceList);
		List<RequestSoaringPriceDto> soaringPriceByWonList = soaringPriceStorer.getSoaringPriceList(latestPriceMap, cheapestPriceMap, SoaringPrice.Unit.WON);
		List<RequestSoaringPriceDto> soaringPriceByPercentList = soaringPriceStorer.getSoaringPriceList(latestPriceMap, cheapestPriceMap, SoaringPrice.Unit.PERCENT);
		soaringPriceStorer.saveSoaringPriceList(soaringPriceByWonList, soaringPriceByPercentList);
	}
}