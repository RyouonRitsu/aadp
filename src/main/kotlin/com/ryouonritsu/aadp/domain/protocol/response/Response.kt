package com.ryouonritsu.aadp.domain.protocol.response

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "Response")
class Response<T>(
    @Schema(name = "success", description = "Success状态", required = true)
    val success: Boolean,
    @Schema(name = "message", description = "信息", required = true)
    val message: String,
    @Schema(name = "data", description = "数据")
    val data: T?
) {
    companion object {
        fun <T> success(message: String): Response<T> {
            return Response(true, message, null)
        }

        fun <T> success(message: String, data: T): Response<T> {
            return Response(true, message, data)
        }

        fun <T> error(message: String): Response<T> {
            return Response(false, message, null)
        }
    }
}