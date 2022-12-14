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
    var institutionId: Long = 0,
    @Column(name = "institution_name")
    var institutionName: String,
    @Column(name = "institution_info")
    var institutionInfo: String,
    @Column(name = "institution_image")
    var institutionImage: String,
    @OneToOne(targetEntity = User::class)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    var institutionCreator: User
) {
    fun toDTO() = InstitutionDTO(
        id = "$institutionId",
        institutionName = institutionName,
        institutionInfo = institutionInfo,
        institutionImage = institutionImage,
        creatorId = "${institutionCreator.id}"
    )
}