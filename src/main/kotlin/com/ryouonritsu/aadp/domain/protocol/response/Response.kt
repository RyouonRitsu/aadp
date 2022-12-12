package com.ryouonritsu.aadp.domain.protocol.response

import com.ryouonritsu.aadp.common.enums.ExceptionEnum
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "Response")
class Response<T>(
    @Schema(name = "code", description = "代码", example = "200", required = true)
    val code: String,
    @Schema(name = "message", description = "信息", example = "Success", required = true)
    val message: String,
    @Schema(name = "data", description = "数据")
    val data: T?
) {
    companion object {
        fun <T> success(message: String): Response<T> =
            Response(ExceptionEnum.SUCCESS.code, message, null)

        fun <T> success(data: T): Response<T> =
            Response(ExceptionEnum.SUCCESS.code, ExceptionEnum.SUCCESS.message, data)

        fun <T> success(message: String, data: T): Response<T> =
            Response(ExceptionEnum.SUCCESS.code, message, data)

        fun <T> failure(message: String): Response<T> =
            Response(ExceptionEnum.UNEXPECTED_ERROR.code, message, null)

        fun <T> failure(exceptionEnum: ExceptionEnum): Response<T> =
            Response(exceptionEnum.code, exceptionEnum.message, null)

        fun <T> failure(exceptionEnum: ExceptionEnum, message: String): Response<T> =
            Response(exceptionEnum.code, message, null)

        fun <T> failure(exceptionEnum: ExceptionEnum, message: String, data: T): Response<T> =
            Response(exceptionEnum.code, message, data)
    }
}