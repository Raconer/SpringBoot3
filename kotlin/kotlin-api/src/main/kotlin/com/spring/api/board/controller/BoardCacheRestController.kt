package com.spring.api.board.controller

import com.spring.api.board.dto.request.BoardCreateRequest
import com.spring.api.board.dto.request.BoardUpdateRequest
import com.spring.api.board.service.BoardService
import com.spring.jpa.mysql.domain.Board
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

@SecurityRequirement(name = "JWT-Auth")
@Tag(name = "Board-Cache", description = "기본 캐시 게시판 API")
@RestController
@RequestMapping("/v1/board/cache")
class BoardCacheRestController(
    // @Qualifier는 주입받는 변수 이름 앞에 적용합니다.
    @Qualifier("boardCacheService")
    private val boardService: BoardService,
) {

    // READ: 목록 조회
    @GetMapping
    fun getList(): List<Board> {
        return this.boardService.getList()
    }

    // READ: 단건 조회
    // Kotlin에서는 Long? (nullable)을 사용하지 않는 한, @PathVariable은 Non-null로 간주됩니다.
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Board {
        return this.boardService.get(id)
    }

    // CREATE
    @PostMapping
    fun create(@RequestBody @Valid request: BoardCreateRequest): Board {
        // request 객체로 게시글 생성 후 반환
        return this.boardService.create(request)
    }

    // UPDATE
    @PutMapping
    fun update(@RequestBody request: BoardUpdateRequest): Board {
        // boardId와 request 객체로 게시글 수정 후 반환
        return this.boardService.update(request)
    }

    // DELETE
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        this.boardService.delete(id)
    }
}