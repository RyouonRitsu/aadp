package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

/**
 * @author ryouonritsu
 */
@Schema(description = "调整用户积分请求")
data class AdjustmentCreditRequest(
    @field:NotNull(message = "UserId is mandatory")
    @Schema(description = "用户id", example = "1", required = true)
    val userId: Long?,
    @field:NotNull(message = "Value is mandatory")
    @Schema(description = "调整值", example = "1", required = true)
    val value: Int?
)
