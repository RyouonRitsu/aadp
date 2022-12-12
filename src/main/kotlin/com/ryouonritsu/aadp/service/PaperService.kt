package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import org.springframework.web.multipart.MultipartFile

/**
 *
 * @author WuKunchao
 */
interface PaperService {
    fun searchPaperByKeyword(keyword: String): Response<List<PaperDTO>>
}