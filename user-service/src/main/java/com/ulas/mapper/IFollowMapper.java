package com.ulas.mapper;

import com.ulas.dto.request.CreateFollowRequestDto;
import com.ulas.repository.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IFollowMapper {
    IFollowMapper INSTANCE = Mappers.getMapper(IFollowMapper.class);
    Follow toFollow(final CreateFollowRequestDto dto, final String userId);
}
