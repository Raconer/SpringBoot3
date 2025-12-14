package com.spring.api.board.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Schema(description = "게시글 수정 요청 데이터 (JSON Body)")
data class BoardUpdateRequest(

    // ID 필드: NotNull, Schema 적용
    @field:NotNull(message = "게시글 ID는 필수입니다.")
    @field:Schema(
        description = "수정할 게시글의 고유 ID",
        example = "15",
        requiredMode = Schema.RequiredMode.REQUIRED // @NotNull이 있으므로 명시적으로 REQUIRED 지정
    )
    val id: Long,

    // TITLE 필드: NotBlank, Size, Schema 적용
    @field:NotBlank(message = "제목은 필수 입력 항목입니다.")
    @field:Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
    @field:Schema(
        description = "수정할 게시글 제목",
        example = "제목을 수정합니다. (기존 값이더라도 반드시 포함)",
        minLength = 1,
        maxLength = 200,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val title: String, // 👈 Kotlin에서는 기본적으로 null을 허용하지 않습니다 (Non-null by default)

    // CONTENT 필드: NotBlank, Schema 적용
    @field:NotBlank(message = "내용은 필수 입력 항목입니다.")
    @field:Schema(
        description = "수정할 게시글 본문 내용",
        example = "내용을 길게 수정했습니다.",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val content: String
)
