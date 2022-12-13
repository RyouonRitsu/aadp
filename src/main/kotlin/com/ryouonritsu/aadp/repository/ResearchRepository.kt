package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Research
import com.ryouonritsu.aadp.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface ResearchRepository :JpaRepository<Research, Long> {
    fun findByResearchTitle(researchTitle : String): Research?

    @Query("SELECT r FROM Research r ORDER BY r.refernum DESC")
    fun findPop(): List<Research>

    @Query("SELECT r FROM Research r ORDER BY r.publishTime DESC")
    fun findLatest(): List<Research>

    fun findAllResearchs(): List<Research>?
    fun findByResearchField(researchField : String): Research?

    fun findByUserId(userId: Long): Research?
//    fun findRefernumMax() : Research?
}