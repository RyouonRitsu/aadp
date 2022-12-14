package com.ryouonritsu.aadp.service.impl

import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.repository.PaperRepository
import com.ryouonritsu.aadp.service.PaperService
import com.ryouonritsu.aadp.utils.RedisUtils
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
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
    companion object {
        private val log = LoggerFactory.getLogger(PaperServiceImpl::class.java)
    }

    override fun searchPaperByKeyword(
        keyword: String,
        page: Int,
        limit: Int
    ): Response<List<PaperDTO>> {
        return runCatching {
            val pageable = PageRequest.of(page - 1, limit)
            var papers = paperRepository.findPapersByPaperTitleLike("%$keyword%", pageable)
            var paperDTOs = arrayListOf<PaperDTO>()
            papers.content.forEach {
                paperDTOs.add(it.toDTO())
            }
            Response.success("获取成功", paperDTOs.toList())
        }.onFailure {
            log.error(it.stackTraceToString())
        }.getOrDefault(
            Response.failure("获取失败, 发生意外错误")
        )
    }

    override fun searchPaperByKeyword(keyword: String): Response<List<PaperDTO>> {
        TODO("Not yet implemented")
    }

    override fun getTop10PaperByClick(): Response<List<PaperDTO>> {
        return runCatching {
            var papers = paperRepository.findTop10ByOrderByPaperClickDesc()
            var paperDTOs = arrayListOf<PaperDTO>()
            papers.forEach {
                paperDTOs.add(it.toDTO())
            }
            Response.success("获取成功", paperDTOs.toList())
        }.onFailure {
            log.error(it.stackTraceToString())
        }.getOrDefault(
            Response.failure("获取失败, 发生意外错误")
        )
    }
}