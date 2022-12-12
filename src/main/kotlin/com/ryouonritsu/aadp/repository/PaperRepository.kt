package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.Paper
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author WuKunchao
 */
interface PaperRepository : JpaRepository<Paper, Long> {
    fun findPapersByPaperTitleLike(keyword: String): List<Paper>
}