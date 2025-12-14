package com.spring.api.board.service.impl;

import com.spring.api.board.dto.request.BoardCreateRequest;
import com.spring.api.board.dto.request.BoardUpdateRequest;
import com.spring.api.board.service.BoardService;
import com.spring.jpa.mysql.domain.Board;
import com.spring.jpa.mysql.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("boardCacheService")
@Transactional
@AllArgsConstructor
public class BoardCacheServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    /*
     * value : 캐시 '그룹' 이름
     * key : '그룹' 내 데이터 식별 '고유 키'
     **/
    @Cacheable(value = "board-list", key = "'all'")
    @Override
    public List<Board> getList() {
        return this.boardRepository.findAll();
    }

    @Cacheable(value = "board-item", key = "#boardId")
    @Override
    public Board get(Long id) {
        return this.boardRepository.findById(id).get();
    }

    @CacheEvict(value = "board-list", allEntries = true)
    @Override
    public Board create(BoardCreateRequest request) {
        Board board = Board.builder()
                .title(request.title())
                .content(request.content())
                .writer(request.writer())
                .build();

        return this.boardRepository.save(board);
    }
    // CachePut: 단건 캐시 (board-item)를 수정된 새 값으로 갱신
    // CacheEvict: 목록 캐시 (board-list)를 전체 무효화 (allEntries=true)하여 최신 목록을 보장
    @CachePut(value = "board-item", key = "#request.id")
    @CacheEvict(value = "board-list", allEntries = true)
    @Override
    public Board update(BoardUpdateRequest request) {
             Board board = this.boardRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."+ request.id()));

        board.update(request.title(), request.content());

        return board;
    }

    @CachePut(value = "board-item", key = "#request.id")
    @CacheEvict(value = "board-list", allEntries = true)
    @Override
    public void delete(Long id) {
        Board board = this.boardRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("삭제할 게시글을 찾을 수 없습니다. ID: " + id));

        boardRepository.delete(board);
    }
}