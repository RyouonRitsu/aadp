package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Research
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


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

    fun findByResearchUserId(userId: Long): Research?
//    fun findRefernumMax() : Research?
}