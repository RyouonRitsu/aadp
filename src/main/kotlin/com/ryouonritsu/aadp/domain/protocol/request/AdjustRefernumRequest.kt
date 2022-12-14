package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull


@Schema(description = "修改研究引用量")
data class AdjustRefernumRequest(
    @field:NotNull(message = "ResearchId is mandatory")
    @Schema(description = "研究id", example = "1", required = true)
    val researchId: Long?,

    @field:NotNull(message = "num is mandatory")
    @Schema(description = "新引用量", example = "1", required = true)
    val num: Int?,

    @field:NotNull(message = "num is mandatory")
    @Schema(description = "当前用户id", example = "1", required = true)
    val id: Long?
)