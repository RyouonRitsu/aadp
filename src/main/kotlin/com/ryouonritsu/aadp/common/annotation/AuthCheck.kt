package com.ryouonritsu.aadp.common.annotation

import com.ryouonritsu.aadp.common.enums.AuthEnum
import com.ryouonritsu.aadp.common.enums.ExceptionEnum
import com.ryouonritsu.aadp.common.exception.ServiceException
import com.ryouonritsu.aadp.repository.UserRepository
import com.ryouonritsu.aadp.utils.RequestContext
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * @author ryouonritsu
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class AuthCheck(
    val auth: Array<AuthEnum> = [AuthEnum.TOKEN]
)

/**
 * @author ryouonritsu
 */
@Aspect
@Component
class AuthCheckAspect(
    private val userRepository: UserRepository
) {
    companion object {
        private val log = LoggerFactory.getLogger(AuthCheckAspect::class.java)
    }

    @Pointcut("@annotation(com.ryouonritsu.aadp.common.annotation.AuthCheck)")
    private fun authCheck() {
    }

    @Before("authCheck()")
    @Throws(Throwable::class)
    fun before(joinPoint: JoinPoint) {
        val authCheck = (joinPoint.signature as MethodSignature).method
            .getAnnotation(AuthCheck::class.java)
        if (AuthEnum.ADMIN in authCheck.auth) {
            val user = userRepository.findById(RequestContext.userId.get()!!)
                .orElseThrow { throw ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
            if (!user.isAdmin) throw ServiceException(ExceptionEnum.PERMISSION_DENIED)
        }
    }
}