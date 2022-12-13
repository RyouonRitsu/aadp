package com.ryouonritsu.aadp.domain.protocol.response

import com.ryouonritsu.aadp.domain.dto.ListAllTaskResultDTO
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "列出所有任务响应")
data class ListAllTaskResponse(
    @Schema(description = "任务结果列表", required = true)
    val tasks: List<ListAllTaskResultDTO>,
    @Schema(description = "总计", required = true)
    val total: String
)
