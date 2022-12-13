package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.AdminTask
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * @author ryouonritsu
 */
interface AdminTaskRepository : JpaRepository<AdminTask, Long> {
    @Query("SELECT a FROM AdminTask a WHERE a.objectType = ?1 AND a.isDeleted = false")
    fun findAllByObjectType(objectType: Int, pageable: Pageable): Page<AdminTask>
}