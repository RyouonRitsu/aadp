package com.ryouonritsu.aadp.common.enums

import com.ryouonritsu.aadp.common.exception.BaseExceptionStructure

/**
 * @author ryouonritsu
 */
enum class ExceptionEnum(
    override val code: String,
    override val message: String
) : BaseExceptionStructure {
    SUCCESS("200", "success"),
    BAD_REQUEST("400", "bad request, please check your request body or parameters"),
    UNAUTHORIZED("401", "unauthorized"),
    FORBIDDEN("403", "forbidden"),
    NOT_FOUND("404", "not found"),
    METHOD_NOT_ALLOWED("405", "method not allowed"),
    NOT_ACCEPTABLE("406", "not acceptable"),
    REQUEST_TIMEOUT("408", "request timeout"),
    INTERNAL_SERVER_ERROR("500", "internal server error"),
    SERVICE_UNAVAILABLE("503", "service unavailable"),
    UNEXPECTED_ERROR("10000", "意想不到的错误发生了！"),
    UNKNOWN_ERROR("10001", "未知错误");
}