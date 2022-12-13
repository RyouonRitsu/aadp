package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "修改用户密码请求")
data class ChangePasswordRequest(
    @Schema(description = "修改模式, 0为忘记密码修改, 1为正常修改", required = true)
    val mode: Int?,
    @Schema(description = "1: 旧密码")
    val oldPassword: String?,
    @Schema(description = "新密码", required = true)
    val password1: String?,
    @Schema(description = "确认新密码", required = true)
    val password2: String?,
    @Schema(description = "0: 邮箱")
    val email: String?,
    @Schema(description = "0: 验证码")
    val verifyCode: String?
)
