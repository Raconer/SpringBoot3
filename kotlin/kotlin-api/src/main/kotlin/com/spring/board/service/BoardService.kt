package com.spring.board.service

import com.spring.jpa.mysql.domain.Board

interface BoardService {
    fun getBoardList(): List<Board>
}