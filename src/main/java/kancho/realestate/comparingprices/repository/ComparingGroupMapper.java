package kancho.realestate.comparingprices.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;

@Mapper
public interface ComparingGroupMapper {

	Long saveComparingGroup(ComparingGroup group);
	List<ComparingGroup> findComparingGroupsByUserNo(Long userNo);
}
