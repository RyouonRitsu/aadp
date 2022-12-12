package com.ryouonritsu.aadp.entity

import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
class AdminTask(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @OneToOne(targetEntity = User::class)
    @JoinColumn(name = "operator_id", referencedColumnName = "id")
    var operator: User,
    @Column(name = "object_id")
    var objectId: Long,
    @Column(name = "object_type", columnDefinition = "TINYINT(3)")
    var objectType: Int,
    @Column(name = "is_deleted", columnDefinition = "TINYINT(3) DEFAULT 0")
    var isDeleted: Boolean = false
) {
}