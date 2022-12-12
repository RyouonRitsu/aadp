package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "修改用户信息请求")
data class ModifyUserInfoRequest(
    @Schema(description = "用户认证令牌", required = true)
    val token: String,
    @Schema(description = "用户名")
    val username: String?,
    @Schema(description = "真实姓名")
    val realName: String?,
    @Schema(description = "个人头像")
    val avatar: String?,
    @Schema(description = "是否认证")
    val isCertified: Boolean?,
    @Schema(description = "学历")
    val educationalBackground: String?,
    @Schema(description = "是否为管理员")
    val isAdmin: Boolean?,
    @Schema(description = "机构ID")
    val institutionId: Long?
)
