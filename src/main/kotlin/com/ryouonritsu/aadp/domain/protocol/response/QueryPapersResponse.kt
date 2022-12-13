package com.ryouonritsu.aadp.domain.protocol.response

import com.ryouonritsu.aadp.domain.dto.PaperDTO
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "查询论文响应")
data class QueryPapersResponse(
    @Schema(description = "论文列表", required = true)
    val papers: List<PaperDTO>,
    @Schema(description = "总数", required = true)
    val total: String
)
