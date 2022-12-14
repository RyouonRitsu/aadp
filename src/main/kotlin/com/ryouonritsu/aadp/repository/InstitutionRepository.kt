package com.ryouonritsu.aadp.repository

import com.ryouonritsu.aadp.entity.Institution
import com.ryouonritsu.aadp.entity.Paper
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
interface InstitutionRepository : JpaRepository<Institution, Long> {
    @Query("SELECT i FROM Institution i WHERE i.institutionName LIKE %?1%")
    fun findByInstitutionNameLike(
        institutionName: String,
        pageable: Pageable = PageRequest.of(0, 1)
    ): Page<Institution>

    @Query("SELECT x FROM Institution x WHERE x.institutionName = ?1 ")
    fun findByInstitutionName(
        institutionName: String,
        pageable: Pageable = PageRequest.of(0, 1)
    ): Page<Institution>

    @Query("SELECT x FROM Institution x WHERE x.id = ?1 ")
    fun findByInstitutionId(
        institutionId: Long,
        pageable: Pageable = PageRequest.of(0, 1)
    ): Page<Institution>

    @Query("SELECT COUNT(x) FROM User x WHERE x.institution.id = ?1")
    fun findMemberNum(institutionId: Long): Long

    @Query("SELECT COUNT(p) FROM Paper p, User u WHERE p.paperAuthorId = u.id and u.institution.id = ?1")
    fun findPaperNum(institutionId: Long): Long

    @Query("SELECT COUNT(p.paperCited) FROM Paper p, User u WHERE p.paperAuthorId = u.id and u.institution.id = ?1")
    fun findCitedNum(institutionId: Long): Long

    @Query("SELECT p FROM Paper p, User u WHERE p.paperAuthorId = u.id and u.institution.id= ?1 ORDER BY p.paperCited DESC")
    fun findPaper(institutionId: Long): List<Paper>

    @Query("SELECT u FROM User u WHERE u.institution.id = ?1")
    fun findMember(institutionId: Long): List<User>
}