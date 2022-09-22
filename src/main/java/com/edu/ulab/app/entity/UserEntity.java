package com.edu.ulab.app.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Сущность Пользователь
 * Getter
 * Setter
 * + Добавлены аннотации для конструкторов
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

    private Long id;

    private String fullName;

    private String title;

    private int age;
}
