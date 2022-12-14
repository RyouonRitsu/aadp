package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime


@Schema(description = "Research entity")
data class ResearchDTO(
    @Schema(description = "research id", example = "1", required = true)
    var id: String = "0",

    @Schema(description = "refernum", example = "100", required = true)
    var refernum: String = "0",

    @Schema(description = "research field", required = true)
    var researchField: String = "",

    @Schema(description = "research title", required = true)
    var researchTitle: String = "",

    @Schema(description = "research abstract", required = true)
    var researchAbstract: String = "",

    @Schema(description = "research content", required = true)
    var researchContent: String = "",

    @Schema(description = "publish time", required = true)
    var publishTime: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "update time", required = true)
    var updateTime: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "user id", example = "1")
    var userId: String? = null,

//    @Schema(description = "user name", example = "lihua")
//    var userName: String? = null,


//    @Schema(description = "user name")
//    var userN
)