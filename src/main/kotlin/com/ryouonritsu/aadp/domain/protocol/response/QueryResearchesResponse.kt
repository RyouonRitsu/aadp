package com.ryouonritsu.aadp.domain.protocol.response

import com.ryouonritsu.aadp.domain.dto.ResearchDTO
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "查询研究响应")
data class QueryResearchesResponse(
    @Schema(description = "研究列表", required = true)
    val researches: List<ResearchDTO>,
    @Schema(description = "总数", required = true)
    val total: String
)
