package com.ryouonritsu.aadp.domain.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author WuKunchao
 */
@Schema(description = "Paper entity")
data class PaperDTO(
    @Schema(description = "paper id", example = "1", required = true)
    var id: String = "0",
    @Schema(description = "paper title", example = "title", required = true)
    var paperTitle: String,
    @Schema(description = "paper author", example = "author", required = true)
    var paperAuthor: String,
    @Schema(description = "paper unit", example = "1", required = true)
    var paperUnit: String,
    @Schema(description = "paper date", example = "1", required = true)
    var paperDate: String,
    @Schema(description = "paper classification", example = "1", required = true)
    var paperClassification: String,
    @Schema(description = "paper cited", example = "0", required = true)
    var paperCited: String,
    @Schema(description = "paper periodical", example = "1", required = true)
    var paperPeriodical: String,
    @Schema(description = "paper abstract", example = "1", required = true)
    var paperAbstract: String,
    @Schema(description = "paper keyword", example = "aaa; bbb; ccc", required = true)
    var paperKeyword: String,
    @Schema(
        description = "paper other authors",
        example = "{\"other_authors\": [{\"author\": \"杨鑫\", \"unit\": \"西北师范大学教育科学学院\"}, {\"author\": \"尚雯\", \"unit\": \"西北师范大学教育科学学院\"}]}",
        required = true
    )
    var paperOtherAuthors: PaperOtherAuthorsDTO,
    @Schema(
        description = "paper other info",
        example = "{\"DOI\": \"10.13694/j.cnki.ddjylt.20221207.001\", \"专辑\": \"社会科学Ⅱ辑\", \"专题\": \"教育理论与教育管理\", \"分类号\": \"G525.1\"}",
        required = true
    )
    var paperOtherInfo: String,
    @Schema(
        description = "paper link",
        example = "https://kns.cnki.net/kcms/detail/detail.aspx?dbcode=CAPJ&dbname=CAPJLAST&filename=JYLT20221206002",
        required = true
    )
    var paperLink: String,
    @Schema(
        description = "paper reference",
        example = "{\"refs\": [{\"ref\": \"论教育仪式中的教师角色及其功能定位[J]. 康翠萍,王之. 教师教育研究. 2021(04)\"}, {\"ref\": \"农村教师乡土情怀的意涵与培育路径[J]. 张立平,程姣姣. 教育学术月刊. 2021(01)\"}]}",
        required = true
    )
    var paperReference: String,
    @Schema(description = "paper click", example = "23", required = true)
    var paperClick: Long,
)
