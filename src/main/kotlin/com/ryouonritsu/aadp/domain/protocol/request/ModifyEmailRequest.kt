package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "修改邮箱请求")
data class ModifyEmailRequest(
    @Schema(description = "新邮箱", required = true)
    val email: String?,
    @Schema(description = "邮箱验证码", required = true)
    val verifyCode: String?,
    @Schema(description = "密码", required = true)
    val password: String?
)
