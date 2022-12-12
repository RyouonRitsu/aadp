package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Research
import com.ryouonritsu.aadp.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ResearchRepository :JpaRepository<Research, Long> {
    fun findByResearchTitle(researchTitle : String): Research?

    fun findAllResearchs(): List<Research>?
    fun findByResearchField(researchField : String): Research?

    fun findByUserId(userId: Long): Research?
//    fun findRefernumMax() : Research?
}