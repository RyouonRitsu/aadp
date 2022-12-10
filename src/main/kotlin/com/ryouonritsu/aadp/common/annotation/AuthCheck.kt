package com.ryouonritsu.aadp.common.annotation

import com.ryouonritsu.aadp.common.enums.AuthEnum

/**
 * @author ryouonritsu
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthCheck(
    val auth: Array<AuthEnum> = [AuthEnum.TOKEN]
)
