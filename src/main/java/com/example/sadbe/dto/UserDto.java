package com.example.sadbe.dto;

import lombok.Data;

@Data
public class UserDto {

    private String login;
    private String password;
    private Boolean isAdmin;

}