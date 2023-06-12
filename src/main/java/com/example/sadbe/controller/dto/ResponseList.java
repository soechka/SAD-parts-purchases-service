package com.example.sadbe.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseList <T> {
    List<T> items;
    Long total;
}
