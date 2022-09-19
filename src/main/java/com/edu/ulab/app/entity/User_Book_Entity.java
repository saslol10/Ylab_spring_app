package com.edu.ulab.app.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Таблица связи между User и User's Books ->     // продумать что у юзера может быть много книг и нужно создать эту связь
 */

@Getter
@Setter
public class User_Book_Entity implements Serializable {
    Long userId;
    Long bookId;

    public User_Book_Entity() {}

    public User_Book_Entity(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
}


