package com.edu.ulab.app.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Сущность Книга
 * Getter
 * Setter
 * + Добавлены аннотации для конструкторов
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity implements Serializable {

    private Long id;

    private Long userId;

    private String title;

    private String author;

    private long pageCount;
}
