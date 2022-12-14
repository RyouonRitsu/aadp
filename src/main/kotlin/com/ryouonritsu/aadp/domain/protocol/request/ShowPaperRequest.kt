package com.ryouonritsu.aadp.domain.protocol.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "输入机构id得到其学术成果")
data class ShowPaperRequest (
    @Schema(description = "id", example = "1", required = true)
    val id: Long
)