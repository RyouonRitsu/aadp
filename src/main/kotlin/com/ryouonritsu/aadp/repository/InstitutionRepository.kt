package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Institution
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author ryouonritsu
 */
interface InstitutionRepository : JpaRepository<Institution, Long> {
}