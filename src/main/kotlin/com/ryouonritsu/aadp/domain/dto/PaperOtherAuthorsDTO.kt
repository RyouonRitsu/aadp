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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PaperAuthorsDTO

        if (author != other.author) return false
        if (unit != other.unit) return false

        return true
    }

    override fun hashCode(): Int {
        var result = author.hashCode()
        result = 31 * result + unit.hashCode()
        return result
    }
}
