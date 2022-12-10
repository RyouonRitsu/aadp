package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "发送找回密码验证码请求")
data class SendForgotPasswordEmailRequest(
    @Schema(description = "邮箱", required = true)
    val email: String?
)
