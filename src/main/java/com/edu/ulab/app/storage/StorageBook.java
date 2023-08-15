package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;

import java.util.List;

/**
 *  Абстракция для хранилища Книг
 */
public interface StorageBook {


    void createBook(BookEntity bookEntity);

    void updateBook(BookEntity bookEntity);

    BookDto getBookById(Long id);

    List<BookDto> getBooksByUserId(Long userId);

    void deleteBookById(Long id);

    void deleteBookByUserId(Long id);
}