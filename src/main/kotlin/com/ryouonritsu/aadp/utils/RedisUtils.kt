package com.ryouonritsu.aadp.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * @author ryouonritsu
 */
@Component
class RedisUtils(
    var redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(RedisUtils::class.java)
    }

    operator fun get(key: String) = redisTemplate.opsForValue().get(key)

    operator fun set(key: String, value: String): Boolean {
        var result = false
        try {
            redisTemplate.opsForValue().set(key, value)
            result = true
        } catch (e: Exception) {
            log.error("RedisUtils.set error: ", e)
        }
        return result
    }

    fun set(key: String, value: String, timeout: Long, unit: TimeUnit): Boolean {
        var result = false
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit)
            result = true
        } catch (e: Exception) {
            log.error("RedisUtils.set error: ", e)
        }
        return result
    }

    fun getAndSet(key: String, value: String): String? {
        var result: String? = null
        try {
            result = redisTemplate.opsForValue().getAndSet(key, value)
        } catch (e: Exception) {
            log.error("RedisUtils.getAndSet error: ", e)
        }
        return result
    }

    operator fun minus(key: String): Boolean {
        var result = false
        try {
            redisTemplate.delete(key)
            result = true
        } catch (e: Exception) {
            log.error("RedisUtils.minus error: ", e)
        }
        return result
    }
}