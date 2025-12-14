package com.spring.api.board.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "게시글 생성 요청 데이터 (Create DTO)")
data class BoardCreateRequest(
    @field:NotBlank(message = "제목은 필수 입력 항목입니다.")
    @field:Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
    @field:Schema(
        description = "게시글의 제목",
        example = "안녕하세요. 첫 게시글입니다.",
        minLength = 1,
        maxLength = 200,
    )
    val title: String,
    @field:NotBlank(message = "내용은 필수 입력 항목입니다.")
    @field:Schema(
        description = "게시글의 본문 내용",
        example = "레코드 클래스를 사용한 DTO 설계는 매우 간결합니다."
    )
    val content: String,
    @field:NotBlank(message = "작성자 정보는 필수 입력 항목입니다.")
    @field:Size(max = 50, message = "작성자는 50자를 초과할 수 없습니다.")
    @field:Schema(
        description = "작성자 이름 또는 ID",
        example = "dev_user_123",
        minLength = 1,
        maxLength = 50
    )
    val writer: String,
)
