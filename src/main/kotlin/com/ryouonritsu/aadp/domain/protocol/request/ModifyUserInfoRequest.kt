package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "修改用户信息请求")
data class ModifyUserInfoRequest(
    @Schema(description = "用户认证令牌", required = true)
    val token: String,
    @Schema(description = "用户名", required = true)
    val username: String? = "",
    @Schema(description = "真实姓名", required = true)
    val realName: String? = "",
    @Schema(description = "个人头像", required = true)
    val avatar: String? = ""
)
