package com.ryouonritsu.aadp.entity

import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
class UserFile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(columnDefinition = "TEXT")
    var url: String,
    @Column(name = "file_path", columnDefinition = "TEXT")
    var filePath: String = "",
    @Column(name = "file_name", columnDefinition = "TEXT")
    var fileName: String = "",
    @OneToOne(targetEntity = User::class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User,
) {
}