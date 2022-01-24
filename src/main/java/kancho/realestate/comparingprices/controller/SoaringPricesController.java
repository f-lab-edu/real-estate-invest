package kancho.realestate.comparingprices.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.response.SuccessReponseDto;
import kancho.realestate.comparingprices.domain.model.SoaringPrice;
import kancho.realestate.comparingprices.service.SoaringPriceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/soaring-prices", produces = "application/json; charset=utf8")
public class SoaringPricesController {
	private final SoaringPriceService soaringPriceService;

	@GetMapping(value = "/percent")
	public ResponseEntity percentList() {
		List<SoaringPrice> soaringPriceList = soaringPriceService.getSoaringPrices(SoaringPrice.Unit.PERCENT);
		return new ResponseEntity<>(new SuccessReponseDto<>("급상승 부동산 가격 조회 성공", soaringPriceList), HttpStatus.OK);
	}

	@GetMapping(value = "/won")
	public ResponseEntity wonList() {
		List<SoaringPrice> soaringPriceList = soaringPriceService.getSoaringPrices(SoaringPrice.Unit.WON);
		return new ResponseEntity<>(new SuccessReponseDto<>("급상승 부동산 가격 조회 성공", soaringPriceList), HttpStatus.OK);
	}
}
