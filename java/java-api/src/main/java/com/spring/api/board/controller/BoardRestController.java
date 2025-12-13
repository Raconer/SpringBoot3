package com.spring.api.board.controller;

import com.spring.api.board.service.BoardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "JWT-Auth")
@Tag(name = "Board", description = "기본 게시판 API")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/board")
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping
    public String index() {
        return this.boardService.getBoardList().toString();
    }
}
