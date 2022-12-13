package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Institution
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * @author ryouonritsu
 */
@Repository
interface InstitutionRepository : JpaRepository<Institution, Long> {
    @Query("SELECT i FROM Institution i WHERE i.institutionName LIKE %?1%")
    fun findByInstitutionNameLike(
        institutionName: String,
        pageable: Pageable = PageRequest.of(0, 1)
    ): Page<Institution>
}