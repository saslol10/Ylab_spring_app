package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * javadoc
 * @author saslol
 */
@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long userId) {
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        userDto.setId(userId);
        userService.updateUser(userDto);
        log.info("Update user: {}", userDto);

        List<Long> createdBookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId( userDto.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        return getUserBookResponse(userId, userDto);
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        UserDto userDto = userService.getUserById(userId);
        log.info("Get user: {}", userDto);
        return getUserBookResponse(userId, userDto);
    }

    private UserBookResponse getUserBookResponse(Long userId, UserDto userDto) {
        List<BookDto> bookList = bookService.getBooksByUserId(userId);
        List<Long> bookIdList = bookList
                .stream()
                .filter(Objects::nonNull)
                .map(BookDto::getId)
                .toList();
        log.info("Collected book: {}", bookIdList);
        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        UserDto userDto = userService.getUserById(userId);
        userService.deleteUserById(userId);
        bookService.deleteBookByUserId(userId);
        log.info("Delete user: {}", userDto);
    }
}
