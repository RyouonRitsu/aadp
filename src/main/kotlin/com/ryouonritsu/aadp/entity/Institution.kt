package com.ryouonritsu.aadp.entity

import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
@Schema(description = "Institution entity")
class Institution(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "institution id", example = "1", required = true)
    var id: Long = 0,
    @Column(name = "institution_name")
    @Schema(description = "institution name", example = "institution name", required = true)
    var institutionName: String,
    @Column(name = "institution_info")
    @Schema(description = "institution info", example = "institution info", required = true)
    var institutionInfo: String,
    @OneToOne(targetEntity = User::class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User
) {
    fun toDict(): Map<String, Any?> = mapOf(
        "institution_id" to "$id",
        "institution_name" to institutionName,
        "institution_info" to institutionInfo,
        "user_id" to user.id
    )
}