package com.ryouonritsu.aadp.domain.dto

import com.ryouonritsu.aadp.common.constants.AADPConstant
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author ryouonritsu
 */
@Schema(description = "简易用户信息")
data class SimpleUserDTO(
    @Schema(description = "用户id", example = "-1", required = true)
    var userId: String = "${AADPConstant.LONG_MINUS_1}",
    @Schema(description = "用户名", example = "username", required = true)
    var username: String,
    @Schema(description = "机构id", example = "-1", required = true)
    var institutionId: String = "${AADPConstant.LONG_MINUS_1}",
    @Schema(description = "机构", example = "institution name", required = true)
    var institutionName: String = AADPConstant.MIDDLE_LINE_STR
)
