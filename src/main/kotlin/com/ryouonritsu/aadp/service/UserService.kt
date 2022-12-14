package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.domain.dto.QueryCooperatorsResultDTO
import com.ryouonritsu.aadp.domain.dto.UserDTO
import com.ryouonritsu.aadp.domain.protocol.request.ModifyUserInfoRequest
import com.ryouonritsu.aadp.domain.protocol.response.AcademicInformationResponse
import com.ryouonritsu.aadp.domain.protocol.response.QueryPapersResponse
import com.ryouonritsu.aadp.domain.protocol.response.QueryResearchesResponse
import com.ryouonritsu.aadp.domain.protocol.response.Response
import org.springframework.web.multipart.MultipartFile

/**
 * @author ryouonritsu
 */
interface UserService {
    fun sendRegistrationVerificationCode(email: String?, modify: Boolean): Response<Unit>
    fun register(
        email: String?,
        verificationCode: String?,
        username: String?,
        password1: String?,
        password2: String?,
        avatar: String,
        realName: String,
    ): Response<Unit>

    fun login(
        username: String?,
        password: String?,
        keepLogin: Boolean
    ): Response<List<Map<String, String>>>

    fun showInfo(userId: Long): Response<List<UserDTO>>
    fun selectUserByUserId(userId: Long): Response<List<UserDTO>>
    fun sendForgotPasswordEmail(email: String?): Response<Unit>
    fun changePassword(
        mode: Int?,
        oldPassword: String?,
        password1: String?,
        password2: String?,
        email: String?,
        verifyCode: String?
    ): Response<Unit>

    fun uploadFile(file: MultipartFile): Response<List<Map<String, String>>>
    fun deleteFile(url: String): Response<Unit>
    fun modifyUserInfo(request: ModifyUserInfoRequest): Response<Unit>
    fun modifyEmail(
        email: String?,
        verifyCode: String?,
        password: String?
    ): Response<Unit>

    fun adjustmentCredit(userId: Long, value: Int): Response<UserDTO>
    fun getAcademicInformation(userId: Long): AcademicInformationResponse
    fun queryPapers(userId: Long, page: Int, limit: Int): QueryPapersResponse
    fun queryResearches(userId: Long, page: Int, limit: Int): QueryResearchesResponse
    fun queryCooperators(userId: Long): List<QueryCooperatorsResultDTO>

    fun claim(institutionName: String, user: Long): Int
}