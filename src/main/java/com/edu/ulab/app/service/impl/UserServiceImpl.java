package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.UserMapperImpl;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.impl.StorageUserImpl;
import com.edu.ulab.app.storage.StorageUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * javadoc
 * @author saslol
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    protected final StorageUser storageUser = new StorageUserImpl();
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        storageUser.saveUser(new UserMapperImpl().userDtoToUserEntity(userDto));
        log.info("Service create user");
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        storageUser.updateUser(new UserMapperImpl().userDtoToUserEntity(userDto));
        log.info("Service update user");
        return userDto;
    }

      @Override
      public UserDto getUserById(Long id) {
          log.info("Service get user");
          return storageUser.getUserById(id);
      }

    @Override
    public void deleteUserById(Long id) {
        log.info("Service delete user");
        storageUser.deleteUserById(id);
    }
}