package kancho.realestate.utils.api.storeaprtment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ApartmentPrice;
import kancho.realestate.comparingprices.repository.ApartmentPriceRepository;
import kancho.realestate.comparingprices.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApartmentApiService {
	private final ApartmentRepository apartmentRepository;
	private final ApartmentPriceRepository apartmentPriceRepository;

	public List<Apartment> findAllApartments() {
		return apartmentRepository.findAll();
	}

	public List<ApartmentPrice> findAllApartmentsPrice() {
		return apartmentPriceRepository.findAll();
	}

	public Apartment save(Apartment apartment) {
		return apartmentRepository.save(apartment);
	}

	public ApartmentPrice save(ApartmentPrice apartmentPrice) {
		return apartmentPriceRepository.save(apartmentPrice);
	}
}
