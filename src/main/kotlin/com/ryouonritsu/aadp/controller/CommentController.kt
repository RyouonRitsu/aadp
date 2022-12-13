package com.ryouonritsu.aadp.controller

import com.ryouonritsu.aadp.common.annotation.AuthCheck
import com.ryouonritsu.aadp.domain.protocol.request.SimpleCommentRequest
import com.ryouonritsu.aadp.entity.Comment
import com.ryouonritsu.aadp.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min

/**
 * @author ryouonritsu
 */
@RestController
@RequestMapping("/comment")
@Tag(name = "评论接口")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("/save")
    @AuthCheck
    @Tag(name = "评论接口")
    @Operation(summary = "保存评论", description = "id留空是新增评论，否则是修改评论，置空不变")
    fun save(@Validated @RequestBody request: Comment) = commentService.save(request)

    @PostMapping("/delete")
    @AuthCheck
    @Tag(name = "评论接口")
    @Operation(summary = "删除评论", description = "根据id删除评论")
    fun delete(@Valid @RequestBody request: SimpleCommentRequest) =
        commentService.delete(request.commentId!!)

    @GetMapping("/queryByPaperId")
    @AuthCheck
    @Tag(name = "评论接口")
    @Operation(summary = "根据论文id查询评论", description = "根据论文id查询评论")
    fun queryByPaperId(
        @RequestParam("paperId") @Parameter(
            description = "论文id",
            required = true
        ) paperId: Long,
        @RequestParam("page") @Parameter(
            description = "页码",
            required = true
        ) @Min(1) page: Int,
        @RequestParam("limit") @Parameter(
            description = "每页数量",
            required = true
        ) @Min(1) limit: Int
    ) = commentService.queryByPaperId(paperId, page, limit)

    @GetMapping("/queryByAuthorId")
    @AuthCheck
    @Tag(name = "评论接口")
    @Operation(summary = "根据作者id查询评论", description = "根据作者id查询评论")
    fun queryByAuthorId(
        @RequestParam("authorId") @Parameter(
            description = "作者id",
            required = true
        ) authorId: Long,
        @RequestParam("page") @Parameter(
            description = "页码",
            required = true
        ) page: Int,
        @RequestParam("limit") @Parameter(
            description = "每页数量",
            required = true
        ) limit: Int
    ) = commentService.queryByAuthorId(authorId, page, limit)

    @PostMapping("/like")
    @AuthCheck
    @Tag(name = "评论接口")
    @Operation(summary = "点赞评论", description = "根据id点赞评论")
    fun like(@Valid @RequestBody request: SimpleCommentRequest) =
        commentService.like(request.commentId!!)
}