package com.ryouonritsu.aadp.entity

import com.ryouonritsu.aadp.domain.dto.PaperDTO
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author WuKunchao
 */
@Entity
class Paper(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var paperTitle: String,
    var paperAuthor: String,
    var paperUnit: String,
    var paperDate: String,
    var paperClassification: String,
    var paperCited: Int,
    var paperPeriodical: String,
    var paperAbstract: String,
    var paperKeyword: String,
    var paperOtherAuthors: String,
    var paperOtherInfo: String,
) {
    fun toDict(): Map<String, Any?> = mapOf(
        "user_id" to "$id",
        "paperTitle" to paperTitle,
        "paperAuthor" to paperAuthor,
        "paperUnit" to paperUnit,
        "paperDate" to paperDate,
        "paperClassification" to paperClassification,
        "paperCited" to paperCited,
        "paperPeriodical" to paperPeriodical,
        "paperAbstract" to paperAbstract,
        "paperKeyword" to paperKeyword,
        "paperOtherAuthors" to paperOtherAuthors,
        "paperOtherInfo" to paperOtherInfo
    )

    fun toDTO() = PaperDTO(
        id = "$id",
        paperTitle = paperTitle,
        paperAuthor = paperAuthor,
        paperUnit = paperUnit,
        paperDate = paperDate,
        paperClassification = paperClassification,
        paperCited = "$paperCited",
        paperPeriodical = paperPeriodical,
        paperAbstract = paperAbstract,
        paperKeyword = paperKeyword,
        paperOtherAuthors = paperOtherAuthors,
        paperOtherInfo = paperOtherInfo
    )
}