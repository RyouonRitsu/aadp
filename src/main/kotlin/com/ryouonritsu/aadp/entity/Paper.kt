package com.ryouonritsu.aadp.entity

import com.alibaba.fastjson2.to
import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.dto.PaperOtherAuthorsDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @author WuKunchao
 */
@Entity
class Paper(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var paperTitle: String,
    var paperAuthorId: Long? = null,
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
        paperOtherInfo = paperOtherInfo
    )
}