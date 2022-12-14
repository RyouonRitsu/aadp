package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.UserFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author ryouonritsu
 */
@Repository
interface UserFileRepository : JpaRepository<UserFile, Long> {
    fun findByUrl(url: String): UserFile?
}