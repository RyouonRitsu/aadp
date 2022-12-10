package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "用户登录请求")
data class LoginRequest(
    @Schema(description = "用户名", example = "Ritsu", required = true)
    val username: String?,
    @Schema(description = "密码", example = "12345678@", required = true)
    val password: String?,
    @Schema(description = "是否记住登录", example = "true", defaultValue = "false")
    val keepLogin: Boolean
)
