package com.ryouonritsu.aadp.service

import org.springframework.web.multipart.MultipartFile

/**
 * @author ryouonritsu
 */
interface UserService {
    fun sendRegistrationVerificationCode(email: String?, modify: Boolean): Map<String, Any>
    fun register(
        email: String?,
        verificationCode: String?,
        username: String?,
        password1: String?,
        password2: String?,
        avatar: String,
        realName: String,
    ): Map<String, Any>

    fun login(username: String?, password: String?, keepLogin: Boolean): Map<String, Any>
    fun showInfo(token: String): Map<String, Any>
    fun selectUserByUserId(userId: Long): Map<String, Any>
    fun sendForgotPasswordEmail(email: String?): Map<String, Any>
    fun changePassword(
        mode: Int?,
        token: String,
        oldPassword: String?,
        password1: String?,
        password2: String?,
        email: String?,
        verifyCode: String?
    ): Map<String, Any>

    fun uploadFile(file: MultipartFile, token: String): Map<String, Any>
    fun deleteFile(token: String, url: String): Map<String, Any>
    fun modifyUserInfo(
        token: String,
        username: String?,
        realName: String?,
        avatar: String?,
    ): Map<String, Any>

    fun modifyEmail(
        token: String,
        email: String?,
        verifyCode: String?,
        password: String?
    ): Map<String, Any>
}