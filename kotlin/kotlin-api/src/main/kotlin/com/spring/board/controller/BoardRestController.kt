package com.spring.board.controller

import com.spring.board.service.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
class BoardRestController(
    private val boardService: BoardService
) {

    @GetMapping
    fun index(): String {
        return this.boardService.getBoardList().toString()
    }
}