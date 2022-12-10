package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "发送注册验证码请求")
data class SendRegistrationVerificationCodeRequest(
    @Schema(description = "邮箱", example = "1780645196@qq.com", required = true)
    val email: String?,
    @Schema(description = "是否修改邮箱", example = "true", defaultValue = "false")
    val modify: Boolean = false
)
