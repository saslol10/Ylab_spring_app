package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.exception.AlreadyExistsException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapperImpl;
import com.edu.ulab.app.storage.StorageBook;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


/**
 * Реализация хранилища Книг
 */
@Slf4j
public class StorageBookImpl implements StorageBook {

    private static final Map<Long, BookEntity> BOOK_ENTITIES = new HashMap<>();
    public void createBook(BookEntity bookEntity){
        if(!BOOK_ENTITIES.containsKey(bookEntity.getId())) {
            BOOK_ENTITIES.put(bookEntity.getId(), bookEntity);
            log.info("Put book in map: {}", bookEntity.getId());
        }else {
            log.info("Didn't put book in map: {}", bookEntity.getId());
            throw new AlreadyExistsException("Book already exists");
        }
    }

    @Override
    public void updateBook(BookEntity bookEntity) {
        if(BOOK_ENTITIES.containsKey(bookEntity.getId()))
        {
            log.info("Update book in map: {}", bookEntity.getId());
            BOOK_ENTITIES.put(bookEntity.getId(),bookEntity);
        }else{
            throw new NotFoundException("Not found book");
        }
    }


    @Override
    public BookDto getBookById(Long id) {
        if(BOOK_ENTITIES.containsKey(id)) {
            log.info("Get book by id: {}", id);
            return new BookMapperImpl().bookEntityToBookDto(BOOK_ENTITIES.get(id));
        }else{
            throw new NotFoundException("Not found Books with that id");
        }
    }

    @Override
    public List<BookDto> getBooksByUserId(Long userId) {
        if(BOOK_ENTITIES.values().stream().filter(Objects::nonNull).anyMatch(x->x.getUserId().equals(userId))) {
            List<BookDto> bookDtos = new ArrayList<>();
            BOOK_ENTITIES.values()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(x -> x.getUserId().equals(userId))
                    .toList()
                    .forEach(x -> bookDtos.add(new BookMapperImpl().bookEntityToBookDto(x)));
            log.info("Get books by userId: {}", userId);
            return bookDtos;
        }else{
            throw new NotFoundException("Not found Books for that userId");
        }
    }

    @Override
    public void deleteBookById(Long id) {
        if(BOOK_ENTITIES.containsKey(id))
        {
            BOOK_ENTITIES.remove(id);
            log.info("Delete book from map by id: {}", id);
        }
        else {
            throw new NotFoundException("Not found Book with that id");
        }
    }

    @Override
    public void deleteBookByUserId(Long userId) {
        if(BOOK_ENTITIES.values().stream().filter(Objects::nonNull).anyMatch(x->x.getUserId().equals(userId)))
        {
            List<BookEntity> bookEntities = BOOK_ENTITIES.values()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(x -> x.getUserId().equals(userId))
                    .toList();
            bookEntities.forEach(x-> BOOK_ENTITIES.remove(x.getId()));
            log.info("Delete book from map by userId: {}", userId);
        }else{
            throw new NotFoundException("Not found Books for that userId");
        }
    }

}
