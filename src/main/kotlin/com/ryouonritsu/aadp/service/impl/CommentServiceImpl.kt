package com.ryouonritsu.aadp.service.impl

import com.ryouonritsu.aadp.common.constants.AADPConstant
import com.ryouonritsu.aadp.common.enums.ExceptionEnum
import com.ryouonritsu.aadp.common.exception.ServiceException
import com.ryouonritsu.aadp.domain.protocol.response.CommentResponse
import com.ryouonritsu.aadp.domain.protocol.response.CommentResultDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.Comment
import com.ryouonritsu.aadp.repository.CommentRepository
import com.ryouonritsu.aadp.repository.UserRepository
import com.ryouonritsu.aadp.service.CommentService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * @author ryouonritsu
 */
@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) : CommentService {
    companion object {
        private val log = LoggerFactory.getLogger(CommentServiceImpl::class.java)
    }

    override fun save(comment: Comment): Response<Unit> {
        if (comment.id == AADPConstant.LONG_MINUS_1) {
            comment.likeCount = 0
            comment.isDeleted = false
            commentRepository.save(comment)
        } else {
            val db = commentRepository.findById(comment.id)
                .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
            db::class.declaredMemberProperties.forEach {
                if (it.name == "createTime") return@forEach
                it.isAccessible = true
                val item = it as KMutableProperty1<*, *>
                if (item.getter.call(comment) != null &&
                    item.getter.call(db) != item.getter.call(comment)
                ) {
                    item.setter.call(db, item.getter.call(comment))
                }
            }
            commentRepository.save(db)
        }
        return Response.success()
    }

    override fun delete(commentId: Long): Response<Unit> {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
        comment.isDeleted = true
        commentRepository.save(comment)
        return Response.success()
    }

    override fun queryByPaperId(paperId: Long, page: Int, limit: Int): Response<CommentResponse> {
        val pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "modifyTime")
        val comments = commentRepository.findByPaperId(paperId, pageable)
        return Response.success(
            CommentResponse(
                comments.content.map {
                    CommentResultDTO(
                        it,
                        userRepository.findById(it.authorId)
                            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
                            .toDTO()
                    )
                },
                "${comments.totalElements}"
            )
        )
    }

    override fun queryByAuthorId(authorId: Long, page: Int, limit: Int): Response<CommentResponse> {
        val pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "modifyTime")
        val comments = commentRepository.findByAuthorId(authorId, pageable)
        return Response.success(
            CommentResponse(
                comments.content.map {
                    CommentResultDTO(
                        it,
                        userRepository.findById(it.authorId)
                            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
                            .toDTO()
                    )
                },
                "${comments.totalElements}"
            )
        )
    }

    override fun like(commentId: Long): Response<Unit> {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
        comment.likeCount = comment.likeCount!! + 1
        commentRepository.save(comment)
        return Response.success()
    }
}