package com.spring.board.service

import com.spring.mysql.domain.Board

interface BoardService {
    fun getBoardList(): List<Board>
}