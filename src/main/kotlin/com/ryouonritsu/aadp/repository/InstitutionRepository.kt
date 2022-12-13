package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Institution
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author ryouonritsu
 */
@Repository
interface InstitutionRepository : JpaRepository<Institution, Long> {
}