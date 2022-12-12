package com.ryouonritsu.aadp.controller

import com.ryouonritsu.aadp.common.annotation.AuthCheck
import com.ryouonritsu.aadp.domain.protocol.request.AdjustRefernumRequest
import com.ryouonritsu.aadp.domain.protocol.request.AdjustmentCreditRequest
import com.ryouonritsu.aadp.domain.protocol.request.ModifyResearchContentRequest
import com.ryouonritsu.aadp.domain.protocol.request.ModifyResearchTitleRequest
import com.ryouonritsu.aadp.service.ResearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/research")
@Tag(name = "研究接口")
class ResearchController(
    private val researchService: ResearchService,
) {
    @PostMapping("/selectResearchByResearchId")
    @Tag(name = "研究接口")
    @Operation(summary = "根据id查询研究信息")
    fun selectResearchByResearchId(
        @RequestParam("research_id") @Parameter(
            description = "研究id",
            required = true
        ) researchId: Long
    ) = researchService.selectResearchByResearchId(researchId)


    @PostMapping("/modifyResearchTitle")
    @AuthCheck
    @Tag(name = "研究接口")
    @Operation(
        summary = "修改研究标题"
    )
    fun modifyResearchTitle(@RequestBody request: ModifyResearchTitleRequest) =
        researchService.modifyResearchTitle(request.researchId!!,request.researchTitle!!)


    @PostMapping("/modifyResearchContent")
    @AuthCheck
    @Tag(name = "研究接口")
    @Operation(
        summary = "修改研究内容"
    )
    fun modifyResearchContent(@RequestBody request: ModifyResearchContentRequest) =
        researchService.modifyResearchContent(request.researchId!!,request.researchContent!!)


    @PostMapping("/adjustRefernum")
    @Tag(name = "研究接口")
    @Operation(
        summary = "调整研究引用数",
    )
    fun adjustRefernum(@Valid @RequestBody request: AdjustRefernumRequest) =
        researchService.adjustRefernum(request.researchId!!, request.num!!)
}