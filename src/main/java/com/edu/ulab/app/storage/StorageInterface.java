package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;

import java.util.List;

/**
 *  // сделать абстракции через которые можно будет производить операции с хранилищем
 */
public interface StorageInterface {
      void saveUser(UserEntity userEntity);
      void updateUser(UserEntity userEntity);
      UserDto getUserById(Long userId);
      void deleteUserById(Long userId);

      void createBook(BookEntity bookEntity);
      void updateBook(BookEntity bookEntity);
      BookDto getBookById(Long id);
      List<BookDto> getBooksByUserId(Long userId);
      void deleteBookById(Long id);

}