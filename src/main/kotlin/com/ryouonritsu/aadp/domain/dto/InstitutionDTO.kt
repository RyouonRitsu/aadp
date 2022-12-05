package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "Institution entity")
data class InstitutionDTO(
    @Schema(description = "institution id", example = "1", required = true)
    var id: Long = 0,
    @Schema(description = "institution name", example = "institution name", required = true)
    var institutionName: String,
    @Schema(description = "institution info", example = "institution info", required = true)
    var institutionInfo: String,
    @Schema(description = "user id", example = "1", required = true)
    var userId: Long
)
