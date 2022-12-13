package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull


@Schema(description = "修改研究标题")
data class ModifyResearchTitleRequest(
    @field:NotNull(message = "ResearchId is mandatory")
    @Schema(description = "研究id", example = "1", required = true)
    val researchId: Long?,
    @field:NotNull(message = "newContent is mandatory")
    @Schema(description = "新标题", example = "title", required = true)
    val researchTitle: String?
)