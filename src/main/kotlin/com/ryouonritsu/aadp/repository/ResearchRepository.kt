package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Research
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author wzm
 */
@Repository
interface ResearchRepository : JpaRepository<Research, Long> {
    fun findByResearchTitle(researchTitle: String): Research?

//    override fun findById(researchId: Long): Optional<Research>

    fun searchById(researchId: Long): Research

    @Query("SELECT r FROM Research r ORDER BY r.refernum DESC")
    fun findPop(): List<Research>

    @Query("SELECT r FROM Research r ORDER BY r.publishTime DESC")
    fun findLatest(): List<Research>

    //    fun findAllResearchs(): List<Research>?
    fun findByResearchField(researchField: String): Research?

    fun findByResearchUserId(userId: Long, pageable: Pageable): Page<Research>
    fun findByResearchUserId(userId: Long): List<Research>
//    fun findRefernumMax() : Research?

    fun countByResearchUserId(userId: Long): Long
}