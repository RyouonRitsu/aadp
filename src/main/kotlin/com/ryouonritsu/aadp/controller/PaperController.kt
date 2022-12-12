package com.ryouonritsu.aadp.controller

import com.ryouonritsu.aadp.service.PaperService
import com.ryouonritsu.aadp.utils.RedisUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author WuKunchao
 */
@RestController
@RequestMapping("/paper")
@Tag(name = "论文接口")
class PaperController(
    private val paperService: PaperService,
    private val redisUtils: RedisUtils
) {
    @GetMapping("/searchPaperByKeyword")
    @Tag(name = "论文接口")
    @Operation(summary = "根据给出的关键词在标题中模糊查询论文")
    fun searchPaperByKeyword(
        @RequestParam("token") @Parameter(
            description = "用户登陆后获取的token令牌",
            required = true
        ) token: String,
        @RequestParam("keyword") @Parameter(
            description = "查询关键词",
            required = true
        ) keyword: String
    ) = paperService.searchPaperByKeyword(keyword)
}