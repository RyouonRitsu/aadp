package com.ryouonritsu.aadp.common.annotation

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject
import com.ryouonritsu.aadp.repository.UserRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * @author ryouonritsu
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Recycle

/**
 * @author ryouonritsu
 */
@Aspect
@Component
class RecycleAspect(
    var userRepository: UserRepository
) {
    val log: Logger = LoggerFactory.getLogger(RecycleAspect::class.java)

    @Pointcut("@annotation(com.ryouonritsu.aadp.common.annotation.Recycle)")
    private fun recycle() {
    }

    @Around("recycle()")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint): Any {
        var result = joinPoint.proceed()
        val jsonObj = JSON.toJSON(result) as JSONObject
        if (jsonObj.containsKey("data")) {
            val data = jsonObj["data"] as JSONArray
            if (jsonObj.containsKey("doc_id")) {
                data.removeAll {
                    val obj = it as JSONObject
                    val docId = (obj["doc_id"] as String).toLong()
                    val doc = try {
                        userRepository.findById(docId).get()
                    } catch (e: NoSuchElementException) {
                        log.info("在完成回收检索时, 数据库中无法找到目标Id为${docId}的文档")
                        null
                    } catch (e: Exception) {
                        log.info("在完成回收检索时, 发生了意外错误")
                        e.printStackTrace()
                        null
                    }
                    true
                }
            }
            result = jsonObj
        }
        return result
    }
}