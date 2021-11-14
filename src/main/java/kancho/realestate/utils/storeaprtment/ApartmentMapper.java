package kancho.realestate.utils.storeaprtment;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.Apartment;

@Mapper
public interface ApartmentMapper {

	void save(Apartment apartment);
	List<Apartment> findAll();
}
