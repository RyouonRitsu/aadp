package com.ryouonritsu.aadp.entity

import com.alibaba.fastjson2.to
import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.dto.PaperOtherAuthorsDTO
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
    @Column(name = "paper_author_id")
    var paperAuthorId: Long? = null,
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
    @Column(name = "paper_abstract", columnDefinition = "mediumtext")
    var paperAbstract: String,
    @Column(name = "paper_keyword")
    var paperKeyword: String,
    @Column(name = "paper_other_authors", columnDefinition = "mediumtext")
    var paperOtherAuthors: String,
    @Column(name = "paper_other_info", columnDefinition = "mediumtext")
    var paperOtherInfo: String,
    @Column(name = "paper_link")
    var paperLink: String,
    @Column(name = "paper_reference", columnDefinition = "mediumtext")
    var paperReference: String,
    @Column(name = "paper_click")
    var paperClick: Long = 0,
) {
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
        paperOtherAuthors = paperOtherAuthors.to<PaperOtherAuthorsDTO>(),
        paperOtherInfo = paperOtherInfo,
        paperLink = paperLink,
        paperReference = paperReference,
        paperClick = paperClick
    )
}