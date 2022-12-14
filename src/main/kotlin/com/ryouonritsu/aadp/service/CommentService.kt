package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.domain.protocol.response.CommentResponse
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.Comment

/**
 * @author ryouonritsu
 */
interface CommentService {
    fun save(comment: Comment): Response<Unit>
    fun delete(commentId: Long): Response<Unit>
    fun queryByPaperId(paperId: Long, page: Int, limit: Int): Response<CommentResponse>
    fun queryByAuthorId(authorId: Long, page: Int, limit: Int): Response<CommentResponse>
    fun like(commentId: Long, value: Int = 1, reverse: Boolean = false): Response<Unit>
}