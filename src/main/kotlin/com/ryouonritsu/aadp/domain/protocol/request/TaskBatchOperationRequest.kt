package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

/**
 * @author ryouonritsu
 */
@Schema(description = "批量操作任务请求")
data class TaskBatchOperationRequest(
    @Schema(
        description = "任务ID列表，逗号连接的字符串，形如1,2,3",
        example = "1,2,3",
        required = true
    )
    val taskIds: List<Long>,
    @field:NotNull(message = "OperationType is mandatory")
    @Schema(description = "操作类型，true表示接受，false表示拒绝", example = "true", required = true)
    val operationType: Boolean?
)
