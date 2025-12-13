package com.spring.board.controller

import com.spring.board.service.BoardService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SecurityRequirement(name = "JWT-Auth")
@Tag(name = "Board", description = "기본 게시판 API")
@RestController
@RequestMapping("/v1/board")
class BoardRestController(
    private val boardService: BoardService
) {

    @GetMapping
    fun index(): String {
        return this.boardService.getBoardList().toString()
    }
}