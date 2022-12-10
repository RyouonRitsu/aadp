package com.ryouonritsu.aadp.entity

import com.ryouonritsu.aadp.domain.dto.UserDTO
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var email: String,
    var username: String,
    var password: String,
    var credit: Long = 0,
    var avatar: String = "",
    @Column(name = "registration_time")
    var registrationTime: LocalDateTime = LocalDateTime.now(),
    @Column(name = "real_name")
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

    fun toDTO() = UserDTO(
        id = "$id",
        email = email,
        username = username,
        password = password,
        credit = "$credit",
        avatar = avatar,
        registrationTime = registrationTime,
        realName = realName,
        institutionId = "${institution?.id}"
    )
}