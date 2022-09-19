package com.edu.ulab.app.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BookEntity implements Serializable {

    private Long id;

    private Long userId;

    private String title;

    private String author;

    private long pageCount;

    public BookEntity() {}

    public BookEntity(Long id, Long userId, String title, String author, long pageCount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
    }
}
