package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.domain.dto.ResearchDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response

interface ResearchService {

    fun createResearch(
        researchTitle: String,
        researchAbstract: String,
        researchContent: String,
        researchField: String,
        researchUserId: Long
    ): Response<Unit>

    //    fun showInfo(researchId: Long) : Response<RDTO>
    fun selectResearchByResearchId(researchId: Long): Response<ResearchDTO>
    fun selectResearchByUserId(userId: Long): Response<List<ResearchDTO>>

    fun selectByDate(startDate:String, endDate: String):Response<List<ResearchDTO>>
    fun selectResearchByResearchField(researchField: String): Response<List<ResearchDTO>>

    fun selectPopResearch(): Response<List<ResearchDTO>>
    fun selectLatestResearch(): Response<List<ResearchDTO>>

    fun modifyResearchContent(researchId: Long, researchContent: String): Response<ResearchDTO>
    fun modifyResearchTitle(researchId: Long, researchTitle: String): Response<ResearchDTO>

    fun adjustRefernum(researchId: Long, num: Int): Response<ResearchDTO>

//    fun selectResearchField(researchField: String):Response<ResearchDTO>
}