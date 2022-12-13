package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "删除文件请求")
data class DeleteFileRequest(
    @Schema(description = "文件分享链接", required = true)
    val url: String
)
