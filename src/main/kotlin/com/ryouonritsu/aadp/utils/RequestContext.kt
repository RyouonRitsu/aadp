package com.ryouonritsu.aadp.utils

object RequestContext {
    var userId = ThreadLocal<Long?>()
}