package com.ryouonritsu.aadp.entity

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
    fun toDict(): Map<String, Any?> = mapOf(
        "institution_id" to "$id",
        "institution_name" to institutionName,
        "institution_info" to institutionInfo,
        "user_id" to user.id
    )
}