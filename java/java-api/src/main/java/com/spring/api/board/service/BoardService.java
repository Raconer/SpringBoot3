package com.spring.api.board.service;

import com.spring.mysql.domain.Board;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList();
}
