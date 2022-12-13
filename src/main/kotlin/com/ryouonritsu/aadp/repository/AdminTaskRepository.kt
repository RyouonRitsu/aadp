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
    @Query("SELECT a FROM AdminTask a WHERE a.isDeleted = false")
    override fun findAll(pageable: Pageable): Page<AdminTask>
}