package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * @author ryouonritsu
 */
@Schema(description = "User entity")
data class UserDTO(
    @Schema(description = "user id", example = "1", required = true)
    var id: Long = 0,
    @Schema(description = "email", example = "email@example.com", required = true)
    var email: String,
    @Schema(description = "username", example = "username", required = true)
    var username: String,
    @Schema(description = "password", example = "password", required = true)
    var password: String,
    @Schema(description = "credit", example = "100", required = true)
    var credit: Long = 0,
    @Schema(description = "avatar", example = "./", required = true)
    var avatar: String = "",
    @Schema(description = "registration time", example = "2007-12-03T10:15:30", required = true)
    var registrationTime: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "real name", example = "real name", required = true)
    var realName: String = "",
    @Schema(description = "institution id", example = "1", required = true)
    var institutionId: Long? = null
)
