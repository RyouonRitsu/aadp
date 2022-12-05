package com.ryouonritsu.aadp.entity

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
@Schema(description = "User entity")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "registration_time")
    @Schema(description = "registration time", example = "2007-12-03T10:15:30", required = true)
    var registrationTime: LocalDateTime = LocalDateTime.now(),
    @Column(name = "real_name")
    @Schema(description = "real name", example = "real name", required = true)
    var realName: String = "",
    @OneToOne(targetEntity = Institution::class)
    @JoinColumn(name = "institution_id", referencedColumnName = "id")
    var institution: Institution? = null
) {
    fun toDict(): Map<String, Any?> = mapOf(
        "user_id" to "$id",
        "email" to email,
        "username" to username,
        "password" to password,
        "credit" to credit,
        "avatar" to avatar,
        "registration_time" to registrationTime,
        "real_name" to realName,
        "institution_id" to (institution?.id ?: -1)
    )
}