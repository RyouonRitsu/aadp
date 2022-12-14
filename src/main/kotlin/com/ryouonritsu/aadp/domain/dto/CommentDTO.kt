package com.ryouonritsu.aadp.domain.dto

import com.ryouonritsu.aadp.common.constants.AADPConstant
import com.ryouonritsu.aadp.common.enums.ObjectEnum
import com.ryouonritsu.aadp.entity.Comment
import com.ryouonritsu.aadp.utils.RequestContext
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

/**
 * @author ryouonritsu
 */
@Schema(description = "评论")
data class CommentDTO(
    @Schema(description = "评论ID")
    var id: Long = AADPConstant.LONG_MINUS_1,
    @Schema(description = "评论内容")
    var content: String?,
    @Schema(description = "评论作者Id")
    var authorId: Long = RequestContext.userId.get()!!,
    @field:NotNull(message = "Object ID is mandatory")
    @Schema(description = "评论ObjectId")
    var objectId: Long?,
    @field:NotNull(message = "Object Type is mandatory")
    @Schema(description = "评论ObjectType")
    var objectType: ObjectEnum?,
    @Schema(description = "点赞数")
    var likeCount: Long?,
    @Schema(description = "是否删除")
    var isDeleted: Boolean?,
    @Schema(description = "创建时间")
    var createTime: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "更新时间")
    var modifyTime: LocalDateTime = LocalDateTime.now()
) {
    constructor(comment: Comment) : this(
        id = comment.id,
        content = comment.content,
        authorId = comment.authorId,
        objectId = comment.objectId,
        objectType = ObjectEnum.valueOf(comment.objectType!!),
        likeCount = comment.likeCount,
        isDeleted = comment.isDeleted,
        createTime = comment.createTime,
        modifyTime = comment.modifyTime
    )
}
