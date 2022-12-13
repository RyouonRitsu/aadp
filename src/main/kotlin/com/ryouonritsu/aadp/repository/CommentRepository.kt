package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author ryouonritsu
 */
@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
}