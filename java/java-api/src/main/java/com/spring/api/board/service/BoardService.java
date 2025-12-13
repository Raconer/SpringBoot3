package com.spring.api.board.service;

import com.spring.api.board.dto.request.BoardCreateRequest;
import com.spring.api.board.dto.request.BoardUpdateRequest;
import com.spring.jpa.mysql.domain.Board;

import java.util.List;

public interface BoardService {
    // READ
    List<Board> getList();
    Board get(Long id);

    // CREATE
    Board create(BoardCreateRequest request);

    // UPDATE
    Board update(BoardUpdateRequest request);

    // DELETE
    void delete(Long id);
}
