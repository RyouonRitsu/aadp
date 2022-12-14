package com.ryouonritsu.aadp.domain.protocol.response

import com.ryouonritsu.aadp.domain.dto.CommentDTO
import com.ryouonritsu.aadp.domain.dto.UserDTO
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "评论响应")
data class CommentResponse(
    @Schema(description = "结果列表", required = true)
    val comments: List<CommentResultDTO>,
    @Schema(description = "总计", required = true)
    val total: String
)

/**
 * @author ryouonritsu
 */
@Schema(description = "评论响应")
data class CommentResultDTO(
    @Schema(description = "评论", required = true)
    val comment: CommentDTO,
    @Schema(description = "作者", required = true)
    val author: UserDTO
)
