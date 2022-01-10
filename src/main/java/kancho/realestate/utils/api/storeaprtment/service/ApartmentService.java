package kancho.realestate.utils.api.storeaprtment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ApartmentPrice;
import kancho.realestate.utils.api.storeaprtment.mapper.ApartmentMapper;
import kancho.realestate.utils.api.storeaprtment.mapper.ApartmentPriceMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApartmentService {
	private final ApartmentMapper apartmentMapper;
	private final ApartmentPriceMapper apartmentPriceMapper;

	public List<Apartment> findAllApartments() {
		return apartmentMapper.findAll();
	}

	public List<ApartmentPrice> findAllApartmentsPrice() {
		return apartmentPriceMapper.findAll();
	}

	public long save(Apartment apartment){
		return apartmentMapper.save(apartment);
	}

	public long save(ApartmentPrice apartmentPrice) {
		return apartmentPriceMapper.save(apartmentPrice);
	}
}
