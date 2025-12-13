package com.spring.api.board.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "게시글 수정 요청 데이터 (JSON Body)")
public record BoardUpdateRequest(
    @NotNull(message = "게시글 ID는 필수입니다.")
    @Schema(description = "수정할 게시글의 고유 ID", example = "15")

    Long id,

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    @Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
    @Schema(description = "수정할 게시글 제목", example = "제목을 수정합니다.")
    String title, // 👈 기존 값이더라도 반드시 포함되어야 함

    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    @Schema(description = "수정할 게시글 본문 내용", example = "내용을 길게 수정했습니다.")
    String content
) {
}
