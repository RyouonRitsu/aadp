package com.ryouonritsu.aadp.service.impl

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.dto.PaperResultInfoDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.Paper
import com.ryouonritsu.aadp.repository.PaperRepository
import com.ryouonritsu.aadp.service.PaperService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.lang.Integer.toHexString

/**
 *
 * @author WuKunchao
 */
@Service
class PaperServiceImpl(
    private val paperRepository: PaperRepository
) : PaperService {
    companion object {
        private val log = LoggerFactory.getLogger(PaperServiceImpl::class.java)
    }

    override fun searchPaperByKeyword(
        keyword: String,
        subject: String?,
        year: String?,
        page: Int,
        limit: Int,
        citedSort: Boolean
    ): Response<List<PaperDTO>> {
        return runCatching {
            val pageable = PageRequest.of(page - 1, limit)
            val paperDTOs = arrayListOf<PaperDTO>()
            val papers: Page<Paper>
            if (subject.isNullOrBlank()) {
                papers = if (year.isNullOrBlank()) {
                    if (citedSort) {
                        paperRepository.findPapersByPaperTitleLikeOrderByPaperCitedDesc("%$keyword%", pageable)
                    } else {
                        paperRepository.findPapersByPaperTitleLike("%$keyword%", pageable)
                    }
                } else {
                    if (citedSort) {
                        paperRepository.findPapersByTitleAndYearLikeOrderByCitedDesc(keyword, year, pageable)
                    } else {
                        paperRepository.findPapersByTitleAndYearLike(keyword, year, pageable)
                    }
                }
                papers.content.forEach {
                    paperDTOs.add(it.toDTO())
                }
            } else {
                val uSubject = subject.toCharArray()
                    .joinToString(separator = "", truncated = "") { "\\\\u${toHexString(it.code)}" }
                papers = if (year.isNullOrBlank()) {
                    if (citedSort) {
                        paperRepository.findPapersByTitleAndSubLikeOrderByCitedDesc(keyword, uSubject, pageable)
                    } else {
                        paperRepository.findPapersByTitleAndSubLike(keyword, uSubject, pageable)
                    }
                } else {
                    if (citedSort) {
                        paperRepository.findPapersByTitleAndSubAndYearLikeOrderByCitedDesc(keyword, uSubject, year, pageable)
                    } else {
                        paperRepository.findPapersByTitleAndSubAndYearLike(keyword, uSubject, year, pageable)
                    }
                }
                papers.content.forEach {
                    paperDTOs.add(it.toDTO())
                }
            }
            Response.success("获取成功", paperDTOs.toList())
        }.onFailure {
            log.error(it.stackTraceToString())
        }.getOrDefault(
            Response.failure("获取失败, 发生意外错误")
        )
    }

    override fun searchPaperByKeyword(keyword: String, subject: String?, year: String?): Response<PaperResultInfoDTO> {
        return runCatching {
            val subs = arrayListOf<String>()
            val papers: List<Paper>
            if (subject.isNullOrBlank()) {
                if (year.isNullOrBlank()) {
                    papers = paperRepository.findPapersByPaperTitleLike("%$keyword%")
                } else {
                    papers = paperRepository.findPapersByTitleAndYearLike(keyword, year)
                }
                papers.forEach {
                    if (it.paperOtherInfo.isNotBlank()) {
                        val infos: JSONObject = JSON.parseObject(it.paperOtherInfo)
                        if (infos.containsKey("专题")) {
                            val str = infos.getString("专题").replace(";", "")
                            val strs = str.split("\\s+".toRegex())
                            subs.addAll(strs)
                        }
                    }
                }
                val paperOtherInfoDTO = PaperResultInfoDTO(papers.size.toString(), subs.toSet().toList())
                Response.success("获取成功", paperOtherInfoDTO)
            } else {
                val uSubject = subject.toCharArray()
                    .joinToString(separator = "", truncated = "") { "\\\\u${toHexString(it.code)}" }
                if (year.isNullOrBlank()) {
                    papers = paperRepository.findPapersByTitleAndSubLike(keyword, uSubject)
                } else {
                    papers = paperRepository.findPapersByTitleAndSubAndYearLike(keyword, uSubject, year)
                }
                val paperOtherInfoDTO = PaperResultInfoDTO(papers.size.toString(), subs)
                Response.success("获取成功", paperOtherInfoDTO)
            }
        }.onFailure {
            log.error(it.stackTraceToString())
        }.getOrDefault(
            Response.failure("获取失败, 发生意外错误")
        )
    }

    override fun getTop10PaperByClick(): Response<List<PaperDTO>> {
        return runCatching {
            val papers = paperRepository.findTop10ByOrderByPaperClickDesc()
            val paperDTOs = arrayListOf<PaperDTO>()
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