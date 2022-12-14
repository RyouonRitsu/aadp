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

    /********** default search **********/

    fun findPapersByPaperTitleLike(keyword: String, pageable: Pageable): Page<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperOtherInfo LIKE %?2%")
    fun findPapersByTitleAndSubLike(
        keyword: String,
        subject: String,
        pageable: Pageable
    ): Page<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperDate LIKE %?2%")
    fun findPapersByTitleAndYearLike(
        keyword: String,
        year: String,
        pageable: Pageable
    ): Page<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperOtherInfo LIKE %?2% AND p.paperDate LIKE %?3%")
    fun findPapersByTitleAndSubAndYearLike(
        keyword: String,
        subject: String,
        year: String,
        pageable: Pageable
    ): Page<Paper>

    /********** cited sort search **********/

    fun findPapersByPaperTitleLikeOrderByPaperCitedDesc(
        keyword: String,
        pageable: Pageable
    ): Page<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperOtherInfo LIKE %?2% ORDER BY p.paperCited DESC")
    fun findPapersByTitleAndSubLikeOrderByCitedDesc(
        keyword: String,
        subject: String,
        pageable: Pageable
    ): Page<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperDate LIKE %?2% ORDER BY p.paperCited DESC")
    fun findPapersByTitleAndYearLikeOrderByCitedDesc(
        keyword: String,
        year: String,
        pageable: Pageable
    ): Page<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperOtherInfo LIKE %?2% AND p.paperDate LIKE %?3% ORDER BY p.paperCited DESC")
    fun findPapersByTitleAndSubAndYearLikeOrderByCitedDesc(
        keyword: String,
        subject: String,
        year: String,
        pageable: Pageable
    ): Page<Paper>

    /********** count search **********/

    fun findPapersByPaperTitleLike(keyword: String): List<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperOtherInfo LIKE %?2%")
    fun findPapersByTitleAndSubLike(
        keyword: String,
        subject: String
    ): List<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperDate LIKE %?2%")
    fun findPapersByTitleAndYearLike(
        keyword: String,
        year: String
    ): List<Paper>

    @Query("SELECT p FROM Paper p WHERE p.paperTitle LIKE %?1% AND p.paperOtherInfo LIKE %?2% AND p.paperDate LIKE %?3%")
    fun findPapersByTitleAndSubAndYearLike(
        keyword: String,
        subject: String,
        year: String
    ): List<Paper>

    /********** other **********/

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