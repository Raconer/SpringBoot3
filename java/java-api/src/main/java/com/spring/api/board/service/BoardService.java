package com.spring.api.board.service;

import com.spring.jpa.mysql.domain.Board;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList();
}
