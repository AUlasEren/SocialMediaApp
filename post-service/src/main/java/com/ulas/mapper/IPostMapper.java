package com.ulas.mapper;

import com.ulas.dto.request.CreateNewPostRequestDto;
import com.ulas.repository.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPostMapper {
    IPostMapper INSTANCE = Mappers.getMapper(IPostMapper.class);
    Post toPost (final CreateNewPostRequestDto dto);
}
