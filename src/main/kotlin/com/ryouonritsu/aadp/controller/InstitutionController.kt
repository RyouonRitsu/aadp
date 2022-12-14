package com.ryouonritsu.aadp.controller

import com.ryouonritsu.aadp.domain.protocol.request.*
import com.ryouonritsu.aadp.service.InstitutionService
import com.ryouonritsu.aadp.utils.RedisUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/institution")
@Tag(name = "机构接口")
class InstitutionController(
    private val institutionService: InstitutionService,
    private val redisUtils: RedisUtils
) {
    @PostMapping("/showBasicInfo")
    @Tag(name = "机构接口")
    @Operation(
        summary = "显示机构的基础信息",
        description = "图片 名称 成员数量"
    )
    fun showBasicInfo(@RequestBody request: ShowBasicInfoRequest) =
        institutionService.showBasicInfo(request.id)

    @PostMapping("/showAcademicInfo")
    @Tag(name = "机构接口")
    @Operation(summary = "展示学术信息 包括发表的学术成果数 总被引数")
    fun showAcademicInfo(@RequestBody request: ShowAcademicInfoRequest) =
        institutionService.showAcademicInfo(request.id)

    @PostMapping("/showPaper")
    @Tag(name = "机构接口")
    @Operation(summary = "返回本机构发表的学术成果")
    fun showPaper(@RequestBody request: ShowPaperRequest) =
        institutionService.showPaper(request.id)

    @PostMapping("/showMember")
    @Tag(name = "机构接口")
    @Operation(summary = "查看本机构的成员")
    fun showMember(@RequestBody request: ShowMemberRequest) =
        institutionService.showMember(request.id)
}