package com.ryouonritsu.aadp.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ryouonritsu.aadp.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author ryouonritsu
 */
@Component
class TokenUtils {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(TokenUtils::class.java)
        private const val EXPIRE_TIME = 10 * 60 * 60 * 1000
        private const val TOKEN_SECRET = "inkBook_backend_secret"

        fun sign(user: User): String {
            val expireAt = Date(System.currentTimeMillis() + EXPIRE_TIME)
            return JWT.create()
                .withIssuer("inkBook_backend")
                .withClaim("user_id", user.id)
                .withExpiresAt(expireAt)
                .sign(Algorithm.HMAC256(TOKEN_SECRET))
        }

        fun verify(token: String): Pair<Boolean, Long> {
            val userId: Long
            try {
                val jwtVerifier =
                    JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("inkBook_backend")
                        .build()
                val decodedJWT = jwtVerifier.verify(token)
                log.info("认证通过")
                userId = decodedJWT.getClaim("user_id").asLong()
                log.info("user_id: $userId")
                log.info("过期时间: ${decodedJWT.expiresAt}")
            } catch (e: Exception) {
                log.error("认证失败")
                log.error(e.message)
                return Pair(false, -1L)
            }
            return Pair(true, userId)
        }
    }
}