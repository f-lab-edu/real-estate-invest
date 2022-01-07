package kancho.realestate.comparingprices.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.repository.ApartmentMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApartmentService {

	private final ApartmentMapper apartmentMapper;

	public List<ResponseApartmentDto> findAllApartments() {
		return apartmentMapper.findAll()
			.stream()
			.map(ResponseApartmentDto::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public ResponseApartmentDto save(RequestApartmentDto requestApartmentDto) {
		Apartment apartment = requestApartmentDto.toApartment();
		apartmentMapper.save(apartment);
		return ResponseApartmentDto.from(apartment);
	}
}
