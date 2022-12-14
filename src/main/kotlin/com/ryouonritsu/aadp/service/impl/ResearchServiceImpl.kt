package com.ryouonritsu.aadp.service.impl

import com.ryouonritsu.aadp.domain.dto.ResearchDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.Research
import com.ryouonritsu.aadp.repository.ResearchRepository
import com.ryouonritsu.aadp.repository.UserRepository
import com.ryouonritsu.aadp.service.ResearchService
import com.ryouonritsu.aadp.utils.RedisUtils.Companion.log
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ResearchServiceImpl(
    private val researchRepository: ResearchRepository,
    private val userRepository: UserRepository,
) : ResearchService {

//    override fun showInfo(researchId: Long): Response<RDTO> {
//        val r = researchRepository.searchById(researchId)
//        val userid = r.researchUserId
//        val u = userRepository.findById(userid)
//    }

    override fun selectByDate(startDate: String, endDate: String): Response<List<ResearchDTO>> {
        val time = " 00:00:00"

        val dt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

        val t1 = LocalDateTime.parse(startDate + time, dt)
        val t2 = LocalDateTime.parse(endDate + time, dt)

        val newL: List<Research>

        val researchList = researchRepository.findAll()
        val tl = mutableListOf<Research>()
        for (l in researchList) {
            if (l.publishTime.isAfter(t1) && l.publishTime.isBefore(t2))
                tl.add(l)
        }
        newL = tl

        if (newL.size == 0) return Response.failure("未找到研究")
        return Response.success("查找成功", newL.map { it.toDTO() })
    }

    override fun selectResearchByResearchId(researchId: Long): Response<ResearchDTO> {
        val r = try {
            researchRepository.searchById(researchId)
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到研究")
        }
        return Response.success("查找成功", r.toDTO())

    }

    override fun selectResearchByUserId(userId: Long): Response<List<ResearchDTO>> {
        val rl = try {
            researchRepository.findByResearchUserId(userId)
        } catch (e: NoSuchElementException) {
            return Response.failure("未找该用户的研究")
        }
        if (rl.size == 0) return Response.failure("未找该用户的研究")
        return Response.success("查找成功", rl.map { it.toDTO() })
    }


    override fun createResearch(
        researchTitle: String,
        researchAbstract: String,
        researchContent: String,
        researchField: String,
        researchUserId: Long
    ): Response<Unit> {
        val r = researchRepository.findByResearchTitle(researchTitle)
        if (r != null) return Response.failure("标题重复")
        researchRepository.save(
            Research(
                researchTitle = researchTitle,
                researchAbstract = researchAbstract,
                researchContent = researchContent,
                researchField = researchField,
                researchUserId = researchUserId,
                doneids = "+"
            )
        )
        return Response.success("创建成功")

    }


    override fun selectPopResearch(): Response<List<ResearchDTO>> {
        log.info("查找最受欢迎研究")
        var researchList = try {
            researchRepository.findPop()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找热门研究")
        }
        if (researchList.size > 10) {
            researchList = researchList.take(10)
        }
//    返回的response？
        return Response.success("查找成功", researchList.map { it.toDTO() })

    }

    override fun selectLatestResearch(): Response<List<ResearchDTO>> {
        log.info("查找最新研究")
        var researchList = try {
            researchRepository.findLatest()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到研究")
        }

        if (researchList.size > 10) {
            researchList = researchList.take(10)
        }
        //返回的response？
        //return Response.success("修改成功", researchList.toDTO()
        return Response.success("查找成功", researchList.map { it.toDTO() })
    }

    override fun selectResearchByResearchField(researchField: String): Response<List<ResearchDTO>> {
        log.info("根据领域查找研究： researchField = $researchField")
        val newL: List<Research>
        try {
            val researchList = researchRepository.findAll()
            System.out.println(researchList)
            val tl = mutableListOf<Research>()
            for (l in researchList) {
//                System.out.println(l.researchField)
//                System.out.println(researchField)
                if (researchField in l.researchField) {
                    tl.add(l)
//                    System.out.println("1")
                }
            }
            newL = tl
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到研究")
        }
        System.out.println(newL)
        if (newL.size == 0) return Response.failure("未找到研究")
        return Response.success("查找成功", newL.map { it.toDTO() })

    }


    override fun adjustRefernum(researchId: Long, num: Int, userId: Long): Response<ResearchDTO> {
        log.info("adjustRefernum: researchId = $researchId, num = $num, userId = $userId")
        var research = try {
            researchRepository.findById(researchId).get()
        } catch (e: NoSuchElementException) {
            return Response.failure("未找到此研究")
        }


        val list = research.doneids.split("+")
        val str = userId.toString()

        if (str in list) {
            return Response.failure("不能重复点赞")
        }

        research.refernum += num
        research.doneids += "+" + str
        research = researchRepository.save(research)
        return Response.success("修改成功", research.toDTO())
    }

    override fun modifyResearchContent(
        researchId: Long,
        researchContent: String
    ): Response<ResearchDTO> {
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

    override fun modifyResearchTitle(
        researchId: Long,
        researchTitle: String
    ): Response<ResearchDTO> {
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