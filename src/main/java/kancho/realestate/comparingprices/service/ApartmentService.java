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
	public Long save(RequestApartmentDto requestApartmentDto) {
		return apartmentMapper.save(requestApartmentDto.toApartment());
	}

	public Apartment findApartmentByDistinguishable(RequestApartmentDto apartmentDto) {
		return apartmentMapper.findByRegionalCodeAndDongAndJibunAndApartmentName(
				apartmentDto.toApartment())
			.orElseThrow(IllegalStateException::new);
	}
}
