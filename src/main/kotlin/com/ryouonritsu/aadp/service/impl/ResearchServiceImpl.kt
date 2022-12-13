package com.ryouonritsu.aadp.service.impl

import com.ryouonritsu.aadp.domain.dto.ResearchDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.Research
import com.ryouonritsu.aadp.entity.User
import com.ryouonritsu.aadp.repository.InstitutionRepository
import com.ryouonritsu.aadp.repository.ResearchRepository
import com.ryouonritsu.aadp.repository.UserRepository
import com.ryouonritsu.aadp.service.ResearchService
import com.ryouonritsu.aadp.utils.RedisUtils
import com.ryouonritsu.aadp.utils.RedisUtils.Companion.log
import okhttp3.internal.filterList
import org.springframework.stereotype.Service
import java.util.*

@Service
abstract class ResearchServiceImpl(
    private val redisUtils: RedisUtils,
    private val researchRepository: ResearchRepository,
    private val userRepository: UserRepository,
    private val institutionRepository: InstitutionRepository
) : ResearchService {

    override fun createResearch(
        researchTitle: String,
        researchAbstract: String,
        researchContent: String,
        researchField: String,
        researchUserId: Long
    ): Response<Unit> {
        val r = researchRepository.findByResearchTitle(researchTitle)
        if(r != null) return Response.failure("标题重复")
        return runCatching {
            researchRepository.save(
                Research(
                    researchTitle = researchTitle,
                    researchAbstract = researchAbstract,
                    researchContent = researchContent,
                    researchField = researchField,
                    researchUserId = researchUserId,
                )
            )
            Response.success("创建成功")
        }.onFailure { ResearchServiceImpl.log.error(it.stackTraceToString()) }
            .getOrDefault(Response.failure("创建失败, 发生意外错误"))
    }

    override fun selectPopResearch(): Response<List<ResearchDTO>> {
        log.info("查找最受欢迎研究")
        var researchList = try {
            researchRepository.findPop()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找热门研究")
        }
//    返回的response？
        return Response.success("查找成功", researchList.map(it.toDTO()))

    }
    override fun selectLatestResearch(): Response<List<ResearchDTO>> {
        log.info("查找最新研究")
        var researchList = try {
            researchRepository.findLatest()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到研究")
        }
        //返回的response？
        //return Response.success("修改成功", researchList.toDTO()
        return Response.success("查找成功", researchList.map(it.toDTO()))
    }

    override fun selectResearchByResearchField(researchField: String): Response<List<ResearchDTO>> {
        log.info("根据领域查找研究： researchField = $researchField")
         try {
            val researchList = researchRepository.findAll()
            val newArr = researchList.filter {
                it.researchField.contains(researchField)
            }.toTypedArray()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到研究")
        }
        return Response.success("查找成功", newArr.map(it.toDTO()))

    }


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