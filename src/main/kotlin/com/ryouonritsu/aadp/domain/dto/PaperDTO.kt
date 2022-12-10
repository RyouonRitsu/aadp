package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author WuKunchao
 */
@Schema(description = "Paper entity")
data class PaperDTO(
    @Schema(description = "paper id", example = "1", required = true)
    var id: String = "0",
    @Schema(description = "paper title", example = "institution name", required = true)
    var paperTitle: String,
    @Schema(description = "paper author", example = "institution info", required = true)
    var paperAuthor: String,
    @Schema(description = "paper unit", example = "1", required = true)
    var paperUnit: String,
    @Schema(description = "paper date", example = "1", required = true)
    var paperDate: String,
    @Schema(description = "paper classification", example = "1", required = true)
    var paperClassification: String,
    @Schema(description = "paper cited", example = "1", required = true)
    var paperCited: String,
    @Schema(description = "paper periodical", example = "1", required = true)
    var paperPeriodical: String,
    @Schema(description = "paper abstract", example = "1", required = true)
    var paperAbstract: String
)
