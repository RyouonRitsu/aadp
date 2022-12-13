package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "查询合作者结果DTO")
data class QueryCooperatorsResultDTO(
    @Schema(description = "合作者", required = true)
    val cooperator: SimpleUserDTO,
    @Schema(description = "合作次数", required = true)
    val count: String
)
