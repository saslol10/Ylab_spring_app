package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.storage.StorageInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDto.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE); // учесть, что при сохранении юзера или книги, должен генерироваться идентификатор
        StorageInterface storageInterface = new Storage();
        storageInterface.createBook(new BookEntity(bookDto.getId(),  bookDto.getUserId(),  bookDto.getTitle(),  bookDto.getAuthor(), bookDto.getPageCount()));
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        StorageInterface storageInterface = new Storage();
        storageInterface.updateBook(new BookEntity(bookDto.getId(),  bookDto.getUserId(),  bookDto.getTitle(),  bookDto.getAuthor(), bookDto.getPageCount()));
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        StorageInterface storageInterface = new Storage();
        return storageInterface.getBookById(id);
    }

    public List<BookDto> getBooksByUserId(Long userId){
        StorageInterface storageInterface = new Storage();
        return storageInterface.getBooksByUserId(userId);
    }
    @Override
    public void deleteBookById(Long id) {
        StorageInterface storageInterface = new Storage();
        storageInterface.deleteBookById(id);
    }
}
