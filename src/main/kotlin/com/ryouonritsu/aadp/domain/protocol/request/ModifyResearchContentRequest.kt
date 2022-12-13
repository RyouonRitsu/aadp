package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

@Schema(description = "修改研究内容")
data class ModifyResearchContentRequest(
    @field:NotNull(message = "ResearchId is mandatory")
    @Schema(description = "研究id", example = "1", required = true)
    val researchId: Long?,
    @field:NotNull(message = "newContent is mandatory")
    @Schema(description = "新内容", example = "content", required = true)
    val researchContent: String?
)