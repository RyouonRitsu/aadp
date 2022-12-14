package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.dto.PaperResultInfoDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response

/**
 *
 * @author WuKunchao
 */
interface PaperService {
    fun searchPaperByKeyword(keyword: String, page: Int, limit: Int): Response<List<PaperDTO>>

    fun searchPaperByKeyword(keyword: String, subject: String, page: Int, limit: Int): Response<List<PaperDTO>>

    fun searchPaperByKeyword(keyword: String): Response<PaperResultInfoDTO>

    fun getTop10PaperByClick(): Response<List<PaperDTO>>
}