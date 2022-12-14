package com.ryouonritsu.aadp.entity

import com.ryouonritsu.aadp.common.constants.AADPConstant
import com.ryouonritsu.aadp.domain.dto.CommentDTO
import com.ryouonritsu.aadp.utils.RequestContext
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author ryouonritsu
 */
@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = AADPConstant.LONG_MINUS_1,
    @Column(length = 512)
    var content: String?,
    var authorId: Long = RequestContext.userId.get()!!,
    var objectId: Long?,
    @Column(columnDefinition = "TINYINT(3) DEFAULT 1", nullable = false)
    var objectType: Int?,
    var likeCount: Long?,
    @Column(columnDefinition = "TINYINT(3) DEFAULT 0", nullable = false)
    var isDeleted: Boolean?,
    var createTime: LocalDateTime = LocalDateTime.now(),
    var modifyTime: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun from(commentDTO: CommentDTO) = Comment(
            id = commentDTO.id,
            content = commentDTO.content,
            authorId = commentDTO.authorId,
            objectId = commentDTO.objectId,
            objectType = commentDTO.objectType?.code,
            likeCount = commentDTO.likeCount,
            isDeleted = commentDTO.isDeleted,
            createTime = commentDTO.createTime,
            modifyTime = commentDTO.modifyTime
        )
    }

    fun toDTO() = CommentDTO(this)
}