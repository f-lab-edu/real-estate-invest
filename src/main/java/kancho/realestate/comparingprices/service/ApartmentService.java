package kancho.realestate.comparingprices.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ApartmentPrice;
import kancho.realestate.comparingprices.repository.ApartmentPriceRepository;
import kancho.realestate.comparingprices.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApartmentService {

	private final ApartmentRepository apartmentRepository;
	private final ApartmentPriceRepository apartmentPriceRepository;

	public List<Apartment> findAllApartments() {
		return apartmentRepository.findAll();
	}

	public List<ApartmentPrice> findAllApartmentsPrice() {
		return apartmentPriceRepository.findAll();
	}

	public List<ResponseApartmentDto> findApartmentDtosWithPaging(Pageable pageable) {
		return apartmentRepository.findAll(pageable)
			.stream()
			.map(ResponseApartmentDto::from)
			.collect(Collectors.toList());
	}

	public List<ResponseApartmentDto> findApartmentDtos() {
		return apartmentRepository.findAll()
			.stream()
			.map(ResponseApartmentDto::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public ResponseApartmentDto save(RequestApartmentDto requestApartmentDto) {
		Apartment apartment = requestApartmentDto.toApartment();
		apartmentRepository.save(apartment);
		return ResponseApartmentDto.from(apartment);
	}

	@Transactional
	public Apartment save(Apartment apartment) {
		return apartmentRepository.save(apartment);
	}

	@Transactional
	public ApartmentPrice save(ApartmentPrice apartmentPrice) {
		return apartmentPriceRepository.save(apartmentPrice);
	}

}
