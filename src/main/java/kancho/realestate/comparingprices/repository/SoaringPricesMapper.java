package kancho.realestate.comparingprices.repository;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import kancho.realestate.comparingprices.domain.model.SoaringPrices;

@Mapper
public interface SoaringPricesMapper {
	List<SoaringPrices> selectSoaringPricesByUnit(String priceDifferenceUnit);
}
