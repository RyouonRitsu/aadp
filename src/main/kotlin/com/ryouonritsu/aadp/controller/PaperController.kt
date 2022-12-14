package com.ryouonritsu.aadp.controller

import com.ryouonritsu.aadp.common.annotation.AuthCheck
import com.ryouonritsu.aadp.service.PaperService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 *
 * @author WuKunchao
 */
@RestController
@RequestMapping("/paper")
@Tag(name = "论文接口")
class PaperController(
    private val paperService: PaperService
) {
    @GetMapping("/searchPaperByKeyword")
    @AuthCheck
    @Tag(name = "论文接口")
    @Operation(summary = "根据给出的关键词在标题中模糊查询论文，可选择是否筛选专题")
    fun searchPaperByKeyword(
        @RequestHeader(
            name = "token",
            required = true
        ) token: String,
        @RequestParam("keyword") @Parameter(
            description = "查询关键词",
            required = true
        ) keyword: String,
        @RequestParam("subject", required = false) @Parameter(
            description = "专题学科关键词"
        ) subject: String?,
        @RequestParam("page", required = false, defaultValue = "1")
        @Parameter(
            description = "查询页数，从1开始，默认为1"
        ) page: Int,
        @RequestParam("limit", required = false, defaultValue = "10")
        @Parameter(
            description = "每页数据条数，默认为10"
        ) limit: Int
    ) = paperService.searchPaperByKeyword(keyword, subject, page, limit)

    @GetMapping("/searchPaperResultInfoByKeyword")
    @AuthCheck
    @Tag(name = "论文接口")
    @Operation(summary = "根据给出的关键词在标题中模糊查询论文的结果，可选择是否筛选专题，返回结果总数和专题（若筛选专题则返回专题为空）")
    fun searchPaperResultInfoByKeyword(
        @RequestHeader(
            name = "token",
            required = true
        ) token: String,
        @RequestParam("keyword") @Parameter(
            description = "查询关键词",
            required = true
        ) keyword: String,
        @RequestParam("subject", required = false) @Parameter(
            description = "专题学科关键词"
        ) subject: String?,
    ) = paperService.searchPaperByKeyword(keyword, subject)

    @GetMapping("/getTop10PaperByClick")
    @AuthCheck
    @Tag(name = "论文接口")
    @Operation(summary = "返回点击量最高的十条论文")
    fun getTop10PaperByClick(
        @RequestHeader(
            name = "token",
            required = true
        ) token: String
    ) = paperService.getTop10PaperByClick()
}