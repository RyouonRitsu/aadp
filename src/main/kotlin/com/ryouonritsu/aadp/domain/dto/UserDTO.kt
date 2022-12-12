package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * @author ryouonritsu
 */
@Schema(description = "User entity")
data class UserDTO(
    @Schema(description = "user id", example = "1", required = true)
    var id: String = "0",
    @Schema(description = "email", example = "email@example.com", required = true)
    var email: String,
    @Schema(description = "username", example = "username", required = true)
    var username: String,
    @Schema(description = "password", example = "password", required = true)
    var password: String,
    @Schema(description = "credit", example = "100", required = true)
    var credit: String = "0",
    @Schema(description = "avatar", example = "./", required = true)
    var avatar: String = "",
    @Schema(description = "registration time", required = true)
    var registrationTime: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "real name", example = "real name", required = true)
    var realName: String = "",
    @Schema(description = "是否认证", example = "false", required = true)
    var isCertified: Boolean = false,
    @Schema(description = "学历", example = "PhD", required = true)
    var educationalBackground: String = "",
    @Schema(description = "是否管理员", example = "false", required = true)
    var isAdmin: Boolean = false,
    @Schema(description = "institution id", example = "1")
    var institutionId: String? = null
)
