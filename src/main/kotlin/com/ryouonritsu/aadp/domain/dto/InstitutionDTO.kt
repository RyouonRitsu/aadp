package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "Institution entity")
data class InstitutionDTO(
    @Schema(description = "institution id", example = "1", required = true)
    var id: String = "0",
    @Schema(description = "institution name", example = "institution name", required = true)
    var institutionName: String,
    @Schema(description = "institution info", example = "institution info", required = true)
    var institutionInfo: String,
    @Schema(description = "institution image", example = "institution image", required = true)
    var institutionImage: String,
    @Schema(description = "creator id", example = "1", required = true)
    var creatorId: String
)
