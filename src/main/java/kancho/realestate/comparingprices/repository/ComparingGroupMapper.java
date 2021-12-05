package kancho.realestate.comparingprices.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.User;

@Mapper
public interface ComparingGroupMapper {

	void saveComparingGroup(ComparingGroup group);
	List<ComparingGroup> findComparingGroupsByUserNo(Long userNo);
}
