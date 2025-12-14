package com.spring.api.board.controller;

import com.spring.api.board.dto.request.BoardCreateRequest;
import com.spring.api.board.dto.request.BoardUpdateRequest;
import com.spring.api.board.service.BoardService;
import com.spring.jpa.mysql.domain.Board;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "JWT-Auth")
@Tag(name = "Board", description = "기본 게시판 API")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/board")
public class BoardRestController {

    @Qualifier("boardService")
    private final BoardService boardService;

    // READ
    @GetMapping
    public List<Board> getList(){
        return this.boardService.getList();
    }

    @GetMapping("/{id}")
    public Board get(@PathVariable Long id) {
        return this.boardService.get(id);
    }

    // CREATE
    @PostMapping
    public Board create(@RequestBody @Valid BoardCreateRequest request) {
        // request 객체로 게시글 생성 후 반환
        return this.boardService.create(request);
    }

    // UPDATE
    @PutMapping
    public Board update(@RequestBody BoardUpdateRequest request) {
        // boardId와 request 객체로 게시글 수정 후 반환
        return this.boardService.update(request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.boardService.delete(id);
    }
}
