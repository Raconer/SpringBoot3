package com.spring.api.board.service.impl

import com.spring.api.board.dto.request.BoardCreateRequest
import com.spring.api.board.dto.request.BoardUpdateRequest
import com.spring.api.board.service.BoardService
import com.spring.jpa.mysql.domain.Board
import com.spring.jpa.mysql.repository.BoardRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service("boardService")
@Transactional
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
) : BoardService {
    override fun getList(): List<Board> {
        val data = this.boardRepository.findAll()
        return data
    }

    override fun get(id: Long): Board {
        return this.boardRepository.findById(id).get()
    }

    override fun create(request: BoardCreateRequest): Board {
        val board = Board(  title = request.title,
            content = request.content,
            writer = request.writer)

        return this.boardRepository.save(board)
    }

    override fun update(request: BoardUpdateRequest): Board {
        val board = boardRepository.findById(request.id)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다." + request.id) }
        board.update(request.title, request.content)

        return board
    }

    override fun delete(id: Long) {
        val board = boardRepository.findById(id)
            .orElseThrow { IllegalArgumentException("삭제할 게시글을 찾을 수 없습니다. ID: $id") }

        boardRepository.delete(board)
    }

}