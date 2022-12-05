package com.ryouonritsu.aadp.entity

import com.ryouonritsu.aadp.domain.dto.InstitutionDTO
import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
class Institution(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "institution_name")
    var institutionName: String,
    @Column(name = "institution_info")
    var institutionInfo: String,
    @OneToOne(targetEntity = User::class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User
) {
    fun toDTO() = InstitutionDTO(
        id = "$id",
        institutionName = institutionName,
        institutionInfo = institutionInfo,
        userId = "${user.id}"
    )
}