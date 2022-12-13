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
    @Schema(description = "paper other authors", example = "{\"other_authors\": [{\"author\": \"杨鑫\", \"unit\": \"西北师范大学教育科学学院\"}, {\"author\": \"尚雯\", \"unit\": \"西北师范大学教育科学学院\"}]}", required = true)
    var paperOtherAuthors: String,
    @Schema(description = "paper other info", example = "...", required = true)
    var paperOtherInfo: String,
    @Schema(description = "paper link", example = "url", required = true)
    var paperLink: String,
    @Schema(description = "paper reference", example = "...", required = true)
    var paperReference: String,
    @Schema(description = "paper click", example = "23", required = true)
    var paperClick: Long,
)
