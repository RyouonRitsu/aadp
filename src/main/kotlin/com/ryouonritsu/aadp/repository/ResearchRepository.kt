package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Research
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ResearchRepository :JpaRepository<Research, Long> {
    fun findByResearchTitle(researchTitle : String): Research?
}