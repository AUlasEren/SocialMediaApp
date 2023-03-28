package com.ulas.mapper;

import com.ulas.dto.request.CreateFollowRequestDto;
import com.ulas.dto.request.NewCreateUSerRequestDto;
import com.ulas.dto.request.UpdateEmailOrUsernameRequestDto;
import com.ulas.dto.request.UserProfileUpdateRequestDto;
import com.ulas.rabbitmq.model.RegisterElasticModel;
import com.ulas.rabbitmq.model.RegisterModel;
import com.ulas.repository.entity.Follow;
import com.ulas.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    UserProfile toUserProfile(final NewCreateUSerRequestDto dto);
    UserProfile toUserProfile(final RegisterModel model);

    RegisterElasticModel toRegisterElasticModel(final UserProfile userProfile);

    UpdateEmailOrUsernameRequestDto toUpdateEmailOrUsernameRequest(final UserProfileUpdateRequestDto dto);


}
