package com.ryouonritsu.aadp.entity

import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
class AdminTaskList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @OneToOne(targetEntity = User::class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User,
    @Column(name = "object_id")
    var objectId: Long,
    @Column(name = "object_type", columnDefinition = "TINYINT(3)")
    var objectType: Int
) {
}