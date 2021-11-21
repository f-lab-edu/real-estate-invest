package kancho.realestate.comparingprices.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.dto.response.ApartmentDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.repository.ApartmentMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApartmentService {

	private final ApartmentMapper apartmentMapper;

	public List<Apartment> findAllApartments() {
		return apartmentMapper.findAll();
	}

	public void save(ApartmentDto apartmentDto){
		apartmentMapper.save(ApartmentDto.toApartment(apartmentDto));
	}
}
