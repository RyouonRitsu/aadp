package com.ryouonritsu.aadp.controller

import com.ryouonritsu.aadp.common.annotation.AuthCheck
import com.ryouonritsu.aadp.domain.protocol.request.*
import com.ryouonritsu.aadp.service.ResearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/research")
@Tag(name = "研究接口")
class ResearchController(
    private val researchService: ResearchService,
) {
    @PostMapping("/createResearch")
    @Tag(name = "研究接口")
    fun createResearch(
        @RequestBody request: CreateRequest
    ) = researchService.createResearch(
        request.researchTitle,
        request.researchAbstract,
        request.researchContent,
        request.researchField,
        request.researchUserId
    )

//    @PostMapping("/showInfo")
//    @Tag(name = "研究接口")
//    @Operation(summary = "返回研究的详情")
//    fun showInfo() = researchService.showInfo(researchId)


    @GetMapping("/selectResearchByResearchId")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(summary = "根据id查询研究信息")
    fun selectResearchByResearchId(
        @RequestParam("research_id") @Parameter(
            description = "研究id",
            required = true
        ) researchId: Long
    ) = researchService.selectResearchByResearchId(researchId)

    @PostMapping("/selectByDate")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(summary = "根据日期范围查询研究")
    fun selectByDate(@RequestBody request: SelectByDateRequest) =
        researchService.selectByDate(request.startDate!!, request.endDate!!)

    @GetMapping("/selectResearchByUserId")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(summary = "根据userId查询研究")
    fun selectResearchByUserId(
        @RequestParam("user_id") @Parameter(
            description = "用户id",
            required = true
        ) userId: Long
    ) = researchService.selectResearchByUserId(userId)

    @GetMapping("/selectResearchByResearchField")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(summary = "根据领域查询研究信息")
    fun selectResearchByResearchField(
        @RequestParam("research_field") @Parameter(
            description = "研究领域",
            required = true
        ) researchField: String
    ) = researchService.selectResearchByResearchField(researchField)

    @PostMapping("/selectPopResearch")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(summary = "找到最受欢迎的研究")
    fun selectPopResearch(
    ) = researchService.selectPopResearch()

    @GetMapping("/selectFPopResearch")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(summary = "找到领域最受欢迎的研究")
    fun selectFPopResearch(
        @RequestParam("research_field") @Parameter(
            description = "领域",
            required = true
        ) researchField: String
    ) = researchService.selectFPopResearch(researchField)


    @PostMapping("/selectLatestResearch")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(summary = "找到最新的研究")
    fun selectLatestResearch(
    ) = researchService.selectLatestResearch()

    @GetMapping("/selectFLatestResearch")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(summary = "找到领域最新的研究")
    fun selectFLatestResearch(
        @RequestParam("research_field") @Parameter(
            description = "领域",
            required = true
        ) researchField: String
    ) = researchService.selectFLatestResearch(researchField)


    @PostMapping("/modifyResearchTitle")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(
        summary = "修改研究标题"
    )
    fun modifyResearchTitle(@RequestBody request: ModifyResearchTitleRequest) =
        researchService.modifyResearchTitle(request.researchId!!, request.researchTitle!!)


    @PostMapping("/modifyResearchContent")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(
        summary = "修改研究内容"
    )
    fun modifyResearchContent(@RequestBody request: ModifyResearchContentRequest) =
        researchService.modifyResearchContent(request.researchId!!, request.researchContent!!)


    @PostMapping("/adjustRefernum")
    @Tag(name = "研究接口")
    @AuthCheck
    @Operation(
        summary = "调整研究引用数",
    )
    fun adjustRefernum(@Valid @RequestBody request: AdjustRefernumRequest) =
        researchService.adjustRefernum(request.researchId!!, request.num!!, request.id!!)
}