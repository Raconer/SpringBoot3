package com.spring.api.board.service.impl;

import com.spring.api.board.service.BoardService;
import com.spring.jpa.mysql.domain.Board;
import com.spring.jpa.mysql.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<Board> getBoardList() {
        this.boardRepository.findAll();
        return List.of();
    }
}
