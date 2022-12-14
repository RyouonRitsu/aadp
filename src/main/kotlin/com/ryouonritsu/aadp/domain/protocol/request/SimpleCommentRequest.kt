package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

/**
 * @author ryouonritsu
 */
@Schema(description = "简易评论请求")
data class SimpleCommentRequest(
    @field:NotNull(message = "Comment ID is mandatory")
    @Schema(description = "评论id", required = true)
    val commentId: Long?,
    @Schema(description = "数量")
    val value: Int = 1,
    @Schema(description = "是否反转")
    val reverse: Boolean = false
)
