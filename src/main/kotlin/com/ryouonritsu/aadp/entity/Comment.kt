package com.ryouonritsu.aadp.entity

import com.ryouonritsu.aadp.common.constants.AADPConstant
import com.ryouonritsu.aadp.utils.RequestContext
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * @author ryouonritsu
 */
@Entity
@Schema(description = "评论")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "评论ID")
    var id: Long = AADPConstant.LONG_MINUS_1,
    @Column(length = 512)
    @Schema(description = "评论内容")
    var content: String?,
    @Schema(description = "评论作者Id")
    var authorId: Long = RequestContext.userId.get()!!,
    @field:NotNull(message = "Paper ID is mandatory")
    @Schema(description = "评论论文Id")
    var paperId: Long?,
    @Schema(description = "点赞数")
    var likeCount: Long?,
    @Column(columnDefinition = "TINYINT(3) DEFAULT 0", nullable = false)
    @Schema(description = "是否删除")
    var isDeleted: Boolean?,
    @Schema(description = "创建时间")
    var createTime: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "更新时间")
    var modifyTime: LocalDateTime = LocalDateTime.now()
) {
}