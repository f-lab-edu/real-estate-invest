package kancho.realestate.comparingprices.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.SuccessReponseDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.service.ApartmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apartments")
public class ApartmentController {

	private final ApartmentService apartmentService;

	@GetMapping(produces = "application/json; charset=utf8")
	public ResponseEntity showApartments() {
		List<ResponseApartmentDto> requestApartmentDtos = apartmentService.findAllApartments();
		return new ResponseEntity<>(new SuccessReponseDto<>("아파트 목록 조회", requestApartmentDtos), HttpStatus.OK);
	}

	@PostMapping(produces = "application/json; charset=utf8")
	public ResponseEntity createApartment(@RequestBody RequestApartmentDto requestApartmentDto) {
		apartmentService.save(requestApartmentDto);
		return new ResponseEntity<>(new SuccessReponseDto<>("아파트가 등록되었습니다.", ""), HttpStatus.CREATED);
	}
}
