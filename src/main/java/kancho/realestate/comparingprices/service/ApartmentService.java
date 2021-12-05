package kancho.realestate.comparingprices.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;
import kancho.realestate.comparingprices.repository.ApartmentMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApartmentService {

	private final ApartmentMapper apartmentMapper;

	public List<ResponseApartmentDto> findAllApartments() {
		return apartmentMapper.findAll()
			.stream()
			.map(ResponseApartmentDto::from)
			.collect(Collectors.toList());
	}

	public void save(RequestApartmentDto requestApartmentDto){
		apartmentMapper.save(RequestApartmentDto.toApartment(requestApartmentDto));
	}
}
