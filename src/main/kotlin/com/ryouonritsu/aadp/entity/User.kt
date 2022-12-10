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
    @Column(name = "is_certified", columnDefinition = "TINYINT(3) DEFAULT 0", nullable = false)
    var isCertified: Boolean = false,
    @Column(name = "educational_background")
    var educationalBackground: String = "",
    @OneToOne(targetEntity = Institution::class)
    @JoinColumn(name = "institution_id", referencedColumnName = "id")
    var institution: Institution? = null
) {
    fun toDTO() = UserDTO(
        id = "$id",
        email = email,
        username = username,
        password = password,
        credit = "$credit",
        avatar = avatar,
        registrationTime = registrationTime,
        realName = realName,
        isCertified = isCertified,
        educationalBackground = educationalBackground,
        institutionId = "${institution?.id}"
    )
}