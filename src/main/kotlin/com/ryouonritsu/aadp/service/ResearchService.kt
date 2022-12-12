package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.domain.dto.ResearchDTO
import com.ryouonritsu.aadp.domain.protocol.request.ModifyUserInfoRequest
import com.ryouonritsu.aadp.domain.protocol.response.Response

interface ResearchService {

    fun selectResearchByResearchId(researchId: Long): Response<List<ResearchDTO>>

    fun modifyResearchContent(researchId: Long, researchContent: String): Response<ResearchDTO>
    fun modifyResearchTitle(researchId: Long, researchTitle: String): Response<ResearchDTO>

    fun adjustRefernum(researchId: Long, num: Int): Response<ResearchDTO>

    fun selectResearchField(researchField: String):
}