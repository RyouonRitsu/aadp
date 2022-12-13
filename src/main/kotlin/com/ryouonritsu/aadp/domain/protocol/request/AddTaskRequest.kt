package com.ryouonritsu.aadp.domain.protocol.request

import com.ryouonritsu.aadp.common.enums.ObjectEnum
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

/**
 * @author ryouonritsu
 */
@Schema(description = "新增管理员审核任务请求")
data class AddTaskRequest(
    @field:NotNull(message = "Token is mandatory")
    @Schema(description = "token", required = true)
    val token: String?,
    @field:NotNull(message = "OperatorType is mandatory")
    @Schema(description = "对象类型，PAPER或INSTITUTION", example = "PAPER", required = true)
    val objectType: ObjectEnum?,
    @field:NotNull(message = "ObjectId is mandatory")
    @Schema(description = "对象id", required = true)
    val objectId: Long?
)
