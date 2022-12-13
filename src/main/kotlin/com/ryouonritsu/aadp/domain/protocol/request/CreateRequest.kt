package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "新建研究请求")
data class CreateRequest(
    @Schema(description = "研究涉及领域", example = "science+physics", required = true)
    val researchField: String?,
    @Schema(description = "研究标题", example = "title", required = true)
    val researchTitle: String?,
    @Schema(description = "研究摘要", example = "abstract", required = true)
    val researchAbstract: String?,
    @Schema(description = "研究内容", example = "content", required = true)
    val researchContent: String?,
    @Schema(description = "所属用户id",required = true)
    val researchUserId: Long
)