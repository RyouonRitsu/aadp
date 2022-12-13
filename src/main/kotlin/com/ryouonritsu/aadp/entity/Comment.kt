package com.ryouonritsu.aadp.entity

import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(length = 512)
    var content: String = "",
    var authorId: Long,
    var paperId: Long,
    var likeCount: Long = 0,
    @Column(columnDefinition = "TINYINT(3) DEFAULT 0", nullable = false)
    var isDeleted: Boolean = false,
    var createTime: LocalDateTime = LocalDateTime.now(),
    var modifyTime: LocalDateTime = LocalDateTime.now()
) {
}