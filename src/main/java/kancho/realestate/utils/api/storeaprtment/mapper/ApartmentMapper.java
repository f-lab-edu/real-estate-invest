package kancho.realestate.utils.api.storeaprtment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.Apartment;

@Mapper
public interface ApartmentMapper {
	long save(Apartment apartment);
	List<Apartment> findAll();
}
