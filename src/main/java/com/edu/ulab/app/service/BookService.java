package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

/**
 * javadoc
 * @author saslol
 */

public interface BookService {
    BookDto createBook(BookDto userDto);

    BookDto updateBook(BookDto userDto);

    BookDto getBookById(Long id);

    List<BookDto> getBooksByUserId(Long userId);

    void deleteBookById(Long id);

    void deleteBookByUserId(Long id);

}
