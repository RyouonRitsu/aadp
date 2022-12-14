package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.User
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
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?

    @Query("SELECT u FROM User u WHERE u.username LIKE %?1% OR u.realName LIKE %?1%")
    fun findByUsernameOrRealNameLike(
        username: String,
        pageable: Pageable = PageRequest.of(0, 1)
    ): Page<User>
}