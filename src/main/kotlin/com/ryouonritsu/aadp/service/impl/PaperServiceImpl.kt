package com.ryouonritsu.aadp.service.impl

import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.repository.PaperRepository
import com.ryouonritsu.aadp.service.PaperService
import com.ryouonritsu.aadp.utils.RedisUtils
import org.springframework.stereotype.Service

/**
 *
 * @author WuKunchao
 */
@Service
class PaperServiceImpl(
    private val paperRepository: PaperRepository,
    private val redisUtils: RedisUtils
) : PaperService {
    override fun searchPaperByKeyword(keyword: String): Response<List<PaperDTO>> {
        return runCatching {
            var papers = paperRepository.findPapersByPaperTitleLike("%$keyword%")
            var paperDTOs = arrayListOf<PaperDTO>()
            papers.forEach {
                paperDTOs.add(it.toDTO())
            }
            Response.success("获取成功", paperDTOs.toList())
        }.onFailure {
            it.printStackTrace()
        }.getOrDefault(
            Response.failure("获取失败, 发生意外错误")
        )
    }
}