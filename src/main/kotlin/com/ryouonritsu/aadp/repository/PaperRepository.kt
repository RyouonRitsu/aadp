package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Paper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author WuKunchao
 */
interface PaperRepository : JpaRepository<Paper, Long> {
    fun findPapersByPaperTitleLike(keyword: String, pageable: Pageable): Page<Paper>
}