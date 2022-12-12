package com.ryouonritsu.aadp.service.impl

import com.ryouonritsu.aadp.domain.dto.ResearchDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.repository.InstitutionRepository
import com.ryouonritsu.aadp.repository.ResearchRepository
import com.ryouonritsu.aadp.repository.UserRepository
import com.ryouonritsu.aadp.service.ResearchService
import com.ryouonritsu.aadp.utils.RedisUtils
import com.ryouonritsu.aadp.utils.RedisUtils.Companion.log
import org.springframework.stereotype.Service
import java.util.*

@Service
abstract class ResearchServiceImpl(
    private val redisUtils: RedisUtils,
    private val researchRepository: ResearchRepository,
    private val userRepository: UserRepository,
    private val institutionRepository: InstitutionRepository
) : ResearchService {


    override fun adjustRefernum(researchId: Long, num: Int): Response<ResearchDTO> {
        log.info("adjustRefernum: researchId = $researchId, num = $num")
        var research = try {
            researchRepository.findById(researchId).get()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到此研究")
        }
        research.refernum = num
        research = researchRepository.save(research)
        return Response.success("修改成功", research.toDTO())
    }

    override fun modifyResearchContent(researchId: Long, researchContent: String): Response<ResearchDTO> {
        log.info("modifyResearchContent researchId = $researchId, researchContent = $researchContent")
        var research = try {
            researchRepository.findById(researchId).get()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到此研究")
        }
        research.researchContent = researchContent
        research = researchRepository.save(research)
        return Response.success("修改成功", research.toDTO())
    }

    override fun modifyResearchTitle(researchId: Long, researchTitle: String): Response<ResearchDTO> {
        log.info("modifyResearchTitle researchId = $researchId, researchTitle = $researchTitle")
        var research = try {
            researchRepository.findById(researchId).get()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到此研究")
        }
        research.researchTitle = researchTitle
        research = researchRepository.save(research)
        return Response.success("修改成功", research.toDTO())
    }
}