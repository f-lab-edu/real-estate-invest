package kancho.realestate.utils.api.storeaprtment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.ApartmentPrice;

@Mapper
public interface ApartmentPriceMapper {
	long save(ApartmentPrice apartmentPrice);
	List<ApartmentPrice> findAll();
}
