package com.ulas.mapper;

import com.ulas.dto.request.NewCreateUSerRequestDto;
import com.ulas.dto.request.UpdateEmailOrUsernameRequestDto;
import com.ulas.dto.request.UserProfileUpdateRequestDto;
import com.ulas.rabbitmq.model.RegisterElasticModel;
import com.ulas.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IElasticMapper {
    IElasticMapper INSTANCE = Mappers.getMapper(IElasticMapper.class);
    UserProfile toUserProfile(final RegisterElasticModel model);


}
