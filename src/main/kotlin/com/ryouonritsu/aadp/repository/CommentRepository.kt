package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * @author ryouonritsu
 */
@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.authorId = ?1 AND c.objectType = ?2 AND c.isDeleted = false")
    fun findByAuthorId(authorId: Long, objectType: Int, pageable: Pageable): Page<Comment>

    @Query("SELECT c FROM Comment c WHERE c.objectId = ?1 AND c.objectType = ?2 AND c.isDeleted = false")
    fun findByPaperId(objectId: Long, objectType: Int, pageable: Pageable): Page<Comment>
}