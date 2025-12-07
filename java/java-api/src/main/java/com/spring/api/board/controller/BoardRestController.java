package com.spring.api.board.controller;

import com.spring.api.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/board")
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping
    public String index() {
        return this.boardService.getBoardList().toString();
    }
}
