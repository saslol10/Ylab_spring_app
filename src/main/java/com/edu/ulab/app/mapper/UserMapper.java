package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.web.request.UserRequest;
import org.mapstruct.Mapper;

/**
 * Добавлены
 * mapper userEntity -> userDto
 * mapper userDto -> userEntity
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    UserDto userEntityToUserDto(UserEntity userEntity);

    UserEntity userDtoToUserEntity(UserDto userDto);

}
