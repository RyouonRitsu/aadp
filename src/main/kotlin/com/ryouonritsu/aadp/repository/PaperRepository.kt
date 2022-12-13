package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Paper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 *
 * @author WuKunchao
 */
@Repository
interface PaperRepository : JpaRepository<Paper, Long> {
    fun findPapersByPaperTitleLike(keyword: String, pageable: Pageable): Page<Paper>
    fun countByPaperAuthorId(userId: Long): Long

    @Query("SELECT SUM(p.paperCited) FROM Paper p WHERE p.paperAuthorId = ?1")
    fun sumPaperCitedByPaperAuthorId(userId: Long): Long

    fun findByPaperAuthorId(userId: Long, pageable: Pageable): Page<Paper>
    fun findByPaperAuthorId(userId: Long): List<Paper>
}