package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;

/**
 * Абстракция для хранилища Пользователей
 */

public interface StorageUser {

    void saveUser(UserEntity userEntity);
    void updateUser(UserEntity userEntity);
    UserDto getUserById(Long userId);
    void deleteUserById(Long userId);
}
