package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.UserFile
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author ryouonritsu
 */
interface UserFileRepository : JpaRepository<UserFile, Long> {
    fun findByUrl(url: String): UserFile?
}