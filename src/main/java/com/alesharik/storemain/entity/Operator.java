package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Админы
 */
@Data
@Entity
@Table(indexes = {
        @Index(columnList = "login", unique = true)
})
public class Operator {
    @Id
    @GeneratedValue
    private long id;
    private String login;
    private String password;
}
