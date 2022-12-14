package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

@Schema(description = "获取日期间的论文")
data class SelectByDateRequest(
    @field:NotNull(message = "startDate is mandatory")
    @Schema(description = "开始日期", example = "2001/1/1", required = true)
    val startDate: String?,
    @field:NotNull(message = "endDate is mandatory")
    @Schema(description = "结束日期", example = "2002/1/1", required = true)
    val endDate: String?
)