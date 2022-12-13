package com.ryouonritsu.aadp.utils

/**
 * @author ryouonritsu
 */
object RequestContext {
    var userId = ThreadLocal<Long?>()
}