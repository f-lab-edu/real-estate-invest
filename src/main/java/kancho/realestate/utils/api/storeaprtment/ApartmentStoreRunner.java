package kancho.realestate.utils.api.storeaprtment;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.scheduler.util.ApartmentStorer;
import lombok.RequiredArgsConstructor;

@Profile("!test") // 테스트시 실행 x
@Component
@RequiredArgsConstructor
public class ApartmentStoreRunner implements ApplicationRunner {

	private final ApartmentStorer apartmentStorer;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 저장할 아파트 정보의 개월 수를 매개변수로 호출 ex) 3 -> 최근 3개월 아파트 거래건들 저장
		apartmentStorer.saveAllOfApartmentInfo(1);
	}
}

