package com.spring.api.board.service.impl;

import com.spring.api.board.dto.request.BoardCreateRequest;
import com.spring.api.board.dto.request.BoardUpdateRequest;
import com.spring.api.board.service.BoardService;
import com.spring.jpa.mysql.domain.Board;
import com.spring.jpa.mysql.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("boardService")
@Transactional
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<Board> getList() {
        return this.boardRepository.findAll();
    }

    @Override
    public Board get(Long id) {
        return this.boardRepository.findById(id).get();
    }

    @Override
    public Board create(BoardCreateRequest request) {
        Board board = Board.builder()
                            .title(request.title())
                            .content(request.content())
                            .writer(request.writer())
                            .build();

        return this.boardRepository.save(board);
    }

    @Override
    public Board update(BoardUpdateRequest request) {
        Board board = this.boardRepository.findById(request.id()).
                        orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."+ request.id()));

        board.update(request.title(), request.content());

        return board;
    }

    @Override
    public void delete(Long id) {
        Board board = this.boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 게시글을 찾을 수 없습니다. ID: " + id));

        boardRepository.delete(board);
    }
}
