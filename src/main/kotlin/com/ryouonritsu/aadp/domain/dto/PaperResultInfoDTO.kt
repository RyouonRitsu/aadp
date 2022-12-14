package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 *
 * @author WuKunchao
 */
@Schema(description = "Paper result info entity")
data class PaperResultInfoDTO(
    @Schema(description = "paper total number", example = "73", required = true)
    var total: String = "0",
    @Schema(description = "paper subject", example = "计算机软件及应用", required = true)
    var paperSubject: List<String>,
)