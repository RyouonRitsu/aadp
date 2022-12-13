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
    @Column(name = "paper_title")
    var paperTitle: String,
    @Column(name = "paper_author")
    var paperAuthor: String,
    @Column(name = "paper_unit")
    var paperUnit: String,
    @Column(name = "paper_date")
    var paperDate: String,
    @Column(name = "paper_classification")
    var paperClassification: String,
    @Column(name = "paper_cited")
    var paperCited: Int,
    @Column(name = "paper_periodical")
    var paperPeriodical: String,
    @Column(name = "paper_abstract", columnDefinition = "longtext")
    var paperAbstract: String,
    @Column(name = "paper_keyword")
    var paperKeyword: String,
    @Column(name = "paper_other_authors", columnDefinition = "longtext")
    var paperOtherAuthors: String,
    @Column(name = "paper_other_info")
    var paperOtherInfo: String,
    @Column(name = "paper_link")
    var paperLink: String,
    @Column(name = "paper_reference")
    var paperReference: String,
    @Column(name = "paper_click")
    var paperClick: Long = 0,
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
        "paperOtherInfo" to paperOtherInfo,
        "paperLink" to paperLink,
        "paperReference" to paperReference,
        "paperClick" to paperClick
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
        paperOtherInfo = paperOtherInfo,
        paperLink = paperLink,
        paperReference = paperReference,
        paperClick = paperClick
    )
}