package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.storage.StorageInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * // так же учесть, что методы хранилища принимают другой тип данных - учесть это в абстракции
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);// учесть, что при сохранении юзера или книги, должен генерироваться идентификатор
        StorageInterface storageInterface = new Storage();
        storageInterface.saveUser(new UserEntity(userDto.getId(), userDto.getFullName(), userDto.getTitle(), userDto.getAge()));
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        StorageInterface storageInterface = new Storage();
        storageInterface.updateUser(new UserEntity(userDto.getId(), userDto.getFullName(), userDto.getTitle(), userDto.getAge()));
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        StorageInterface storageInterface = new Storage();
        return storageInterface.getUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        StorageInterface storageInterface = new Storage();
        storageInterface.deleteUserById(id);
    }
}