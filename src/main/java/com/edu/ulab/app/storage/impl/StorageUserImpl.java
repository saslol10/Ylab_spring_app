package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.AlreadyExistsException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapperImpl;
import com.edu.ulab.app.storage.StorageUser;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Реализация хранилища Пользователя
 */

@Slf4j
public class StorageUserImpl implements StorageUser {
    private static final Map<Long, UserEntity> USER_ENTITIES = new HashMap();

    @Override
    public void saveUser(UserEntity userEntity) {
        if (!USER_ENTITIES.containsKey(userEntity.getId()))
        {
            USER_ENTITIES.put(userEntity.getId(), userEntity);
        log.info("Put user in map: {}", userEntity);
        }
        else {
            log.info("Didn't put user in map: {}", userEntity);
            throw new AlreadyExistsException("User already exists");
        }
    }

    @Override
    public void updateUser(UserEntity userEntity)
    {
        if(USER_ENTITIES.containsKey(userEntity.getId()))
        {
            log.info("Update user in map: {}", userEntity );
            USER_ENTITIES.put(userEntity.getId(),userEntity);
        }else{
            throw new NotFoundException("Not found User");
        }
    }

    @Override
    public UserDto getUserById(Long userId) {
        if(USER_ENTITIES.containsKey(userId)) {
            log.info("Get user from map by id: {}", userId);
            return new UserMapperImpl().userEntityToUserDto(USER_ENTITIES.get(userId));
        }else{
            throw new NotFoundException("Not found User with that id");
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        if(USER_ENTITIES.containsKey(userId))
        {
            USER_ENTITIES.remove(userId);
            log.info("Delete user from map by id: {}", userId);
        }
        else {
            throw new NotFoundException("Not found User with that id");
        }
    }
}
