package com.ryouonritsu.aadp.domain.dto

/**
 * @author ryouonritsu
 */
data class PaperOtherAuthorsDTO(
    var otherAuthors: List<PaperAuthorsDTO>
)

/**
 * @author ryouonritsu
 */
data class PaperAuthorsDTO(
    var author: String,
    var unit: String
)
