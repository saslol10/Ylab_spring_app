package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.web.request.BookRequest;
import org.mapstruct.Mapper;

/**
 * Добавлены
 * mapper bookEntity -> bookDto
 * mapper bookDto -> bookEntity
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookRequestToBookDto(BookRequest bookRequest);

    BookRequest bookDtoToBookRequest(BookDto bookDto);

    BookDto bookEntityToBookDto(BookEntity bookEntity);

    BookEntity bookDtoToBookEntity(BookDto bookDto);

}
