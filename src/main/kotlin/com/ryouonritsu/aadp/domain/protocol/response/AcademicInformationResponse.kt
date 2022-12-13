package com.ryouonritsu.aadp.domain.protocol.response

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "学术信息响应")
data class AcademicInformationResponse(
    @Schema(description = "学术成果数", example = "1", required = true)
    val academicCount: String,
    @Schema(description = "研究数", example = "1", required = true)
    val researchCount: String,
    @Schema(description = "总被引用次数", example = "1", required = true)
    val totalCitations: String
)
