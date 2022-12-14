package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "输入机构名进行认领 若不存在 请求创建新的机构")
class ClaimRequest(
    @Schema(description = "institutionName", example = "BUAA", required = true)
    val institutionName: String,
    @Schema(description = "user", example = 1.toString(), required = true)
    val userId: Long
)