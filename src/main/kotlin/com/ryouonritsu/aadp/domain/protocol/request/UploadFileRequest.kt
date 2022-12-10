package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

/**
 * @author ryouonritsu
 */
@Schema(description = "上传文件请求")
data class UploadFileRequest(
    @Schema(description = "文件", required = true)
    val file: MultipartFile,
    @Schema(description = "用户认证令牌", required = true)
    val token: String
)
