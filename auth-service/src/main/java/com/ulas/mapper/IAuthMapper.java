package com.ulas.mapper;

import com.ulas.dto.request.NewCreateUSerRequestDto;
import com.ulas.dto.request.RegisterRequestDto;
import com.ulas.dto.response.RegisterResponseDto;
import com.ulas.rabbitmq.model.RegisterMailModel;
import com.ulas.rabbitmq.model.RegisterModel;
import com.ulas.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);
    Auth toAuth(final RegisterRequestDto dto);
    RegisterResponseDto toRegisterResponseDto(final Auth auth);
    @Mapping(source = "id",target = "authId") // id to authId set
    NewCreateUSerRequestDto toNewCreateUSerRequestDto(final Auth auth);

    @Mapping(source = "id",target = "authId")
    RegisterModel toRegisterModel(final Auth auth);
    RegisterMailModel toRegisterMailModel(final Auth auth);

}
