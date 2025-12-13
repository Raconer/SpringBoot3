package com.spring.api.board.controller;

import com.spring.api.board.dto.request.BoardCreateRequest;
import com.spring.api.board.dto.request.BoardUpdateRequest;
import com.spring.api.board.service.BoardService;
import com.spring.jpa.mysql.domain.Board;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "JWT-Auth")
@Tag(name = "Board-Cache", description = "기본 캐시 게시판 API")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/board/cache")
public class BoardCacheRestController {

    @Qualifier("boardCacheService")
    private final BoardService boardCacheService;

    // READ
    @GetMapping
    public List<Board> getList(){
        return this.boardCacheService.getList();
    }

    @GetMapping("/{id}")
    public Board get(@PathVariable Long id) {
        return this.boardCacheService.get(id);
    }

    // CREATE
    @PostMapping
    public Board create(@RequestBody @Valid BoardCreateRequest request) {
        // request 객체로 게시글 생성 후 반환
        return this.boardCacheService.create(request);
    }

    // UPDATE
    @PutMapping
    public Board update(@RequestBody BoardUpdateRequest request) {
        // boardId와 request 객체로 게시글 수정 후 반환
        return this.boardCacheService.update(request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.boardCacheService.delete(id);
    }
}
