package com.spring.api.board.service

import com.spring.api.board.dto.request.BoardCreateRequest
import com.spring.api.board.dto.request.BoardUpdateRequest
import com.spring.jpa.mysql.domain.Board

interface BoardService {
    // READ
    fun getList(): List<Board>
    fun get(id: Long): Board

    // CREATE
    fun create(request: BoardCreateRequest): Board

    // UPDATE
    fun update(request: BoardUpdateRequest): Board

    // DELETE
    fun delete(id: Long)
}