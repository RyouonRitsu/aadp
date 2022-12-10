package com.ryouonritsu.aadp.common.exception

import com.ryouonritsu.aadp.common.enums.ExceptionEnum

/**
 * @author ryouonritsu
 */
class ServiceException : RuntimeException {
    var code: String? = null
    override var message: String? = null

    constructor() : super()
    constructor(exceptionEnum: ExceptionEnum) : super(exceptionEnum.code) {
        this.code = exceptionEnum.code
        this.message = exceptionEnum.message
    }
}