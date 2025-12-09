package com.spring.board.service.impl

import com.spring.board.service.BoardService
import com.spring.mysql.domain.Board
import com.spring.mysql.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository
) : BoardService {

    override fun getBoardList(): List<Board> {
        this.boardRepository.findAll();
        return listOf()
    }
}