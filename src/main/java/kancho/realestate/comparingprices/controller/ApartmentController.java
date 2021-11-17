package kancho.realestate.comparingprices.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.response.ApartmentDto;
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
	public ResponseEntity showApartments(HttpServletRequest request) {
		List<Apartment> apartments = apartmentService.findAllApartments();
		List<ApartmentDto> apartmentDtos = apartments.stream().map(ApartmentDto::of).collect(Collectors.toList());
		return new ResponseEntity<>(new SuccessReponseDto<>("아파트 목록 조회", apartmentDtos), HttpStatus.OK);
	}
}
