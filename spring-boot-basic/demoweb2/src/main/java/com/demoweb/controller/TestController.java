package com.demoweb.controller;

import com.demoweb.dto.BoardDto;

public class TestController {

    public void doBuilderTest() {
        BoardDto board = BoardDto.builder()
                        .boardNo(1)
                        .title("Builder Pattern")
                        .writer("userone")
                        .content("test")
                        .build();
    }

}
