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

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperOtherInfo LIKE %?2%")
    fun findPapersByPaperTitleLike(
        keyword: String,
        subject: String,
        pageable: Pageable
    ): Page<Paper>

    fun findPapersByPaperTitleLike(keyword: String): List<Paper>

    fun findTop10ByOrderByPaperClickDesc(): List<Paper>

    fun countByPaperAuthorId(userId: Long): Long

    @Query(
        "SELECT IFNULL(SUM(p.paper_cited), 0) FROM paper p WHERE p.paper_author_id = ?1",
        nativeQuery = true
    )
    fun sumPaperCitedByPaperAuthorId(userId: Long): Long

    fun findByPaperAuthorId(userId: Long, pageable: Pageable): Page<Paper>
    fun findByPaperAuthorId(userId: Long): List<Paper>
}