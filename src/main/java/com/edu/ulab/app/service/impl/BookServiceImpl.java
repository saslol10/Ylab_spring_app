package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.mapper.BookMapperImpl;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.impl.StorageBookImpl;
import com.edu.ulab.app.storage.StorageBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * javadoc
 * @author saslol
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

    protected final StorageBook storageBook = new StorageBookImpl();

    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDto.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        storageBook.createBook(new BookMapperImpl().bookDtoToBookEntity(bookDto));
        log.info("Service create book");
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        storageBook.updateBook(new BookMapperImpl().bookDtoToBookEntity(bookDto));
        log.info("Service update book");
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Service get book by id");
        return storageBook.getBookById(id);
    }

    public List<BookDto> getBooksByUserId(Long userId) {
        log.info("Service get books by userId");
        return storageBook.getBooksByUserId(userId);
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Service delete book");
        storageBook.deleteBookById(id);
    }

    @Override
    public void deleteBookByUserId(Long id) {
        log.info("Service delete books by userId");
        storageBook.deleteBookByUserId(id);
    }
}
