package com.sparta.blog.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class exceptionDto {
    private String errorMessage;
    private int statusCode;
}
