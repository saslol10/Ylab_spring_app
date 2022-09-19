package com.edu.ulab.app.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserEntity implements Serializable {

    private Long id;

    private String fullName;

    private String title;

    private int age;

    public UserEntity() {
    }

    public UserEntity(Long id, String fullName, String title, int age) {
        this.id = id;
        this.fullName = fullName;
        this.title = title;
        this.age = age;
    }
}
