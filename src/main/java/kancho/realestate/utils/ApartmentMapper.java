package kancho.realestate.utils;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.Apartment;

@Mapper
public interface ApartmentMapper {

	void save(Apartment apartment);
	List<Apartment> findAll();
}
