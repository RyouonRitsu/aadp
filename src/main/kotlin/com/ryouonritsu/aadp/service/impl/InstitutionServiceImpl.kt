package com.ryouonritsu.aadp.service.impl

import com.ryouonritsu.aadp.domain.dto.InstitutionDTO
import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.Institution
import com.ryouonritsu.aadp.entity.User
import com.ryouonritsu.aadp.repository.InstitutionRepository
import com.ryouonritsu.aadp.repository.UserRepository
import com.ryouonritsu.aadp.service.InstitutionService
import com.ryouonritsu.aadp.utils.RedisUtils.Companion.log
import org.springframework.stereotype.Service

@Service
class InstitutionServiceImpl (
    private val institutionRepository: InstitutionRepository,
    private val userRepository: UserRepository,
) : InstitutionService {
    override fun createInstitution(
        institutionName: String,
        institutionInfo: String,
        institutionImage: String,
        institutionId: Long,
        institutionCreator: User
    ): Response<Unit>{
        var r = institutionRepository.findByInstitutionName(institutionName)
        if(r!= null) return Response.failure("机构名重复")
        institutionRepository.save(
            Institution(
                institutionName = institutionName,
                institutionInfo = institutionInfo,
                institutionImage = institutionImage,
                institutionId = institutionId,
                institutionCreator = institutionCreator
            )
        )
        return Response.success("创建机构成功!")
    }

    override fun showBasicInfo(institutionId: Long): Response<List<InstitutionDTO>> {
        log.info("显示机构基本信息")
        var institution = try{
            institutionRepository.findByInstitutionId(institutionId)
        }catch (e: NoSuchElementException){
            return Response.failure("未找到该ID的机构")
        }
        var list = listOfNotNull(institution!!.toDTO())
        return Response.success("查找成功", list)
    }

    override fun showMemberNum(institutionId: Long): Long {
        log.info("查询机构成员数量")
        return institutionRepository.findMemberNum(institutionId)
    }

    override fun showPaperNum(institutionId: Long): Long {
        log.info("查询机构学术成果数量")
        return institutionRepository.findMemberNum(institutionId)
    }

    override fun showCitedNum(institutionId: Long): Long {
        log.info("查询机构学术成果被引次数")
        return institutionRepository.findCitedNum(institutionId)
    }

    override fun showPaper(institutionId: Long): Response<List<PaperDTO>> {
        log.info("查询机构下所属论文")
        var paperList = institutionRepository.findPaper(institutionId)
        if(paperList.size > 10){
            paperList = paperList.take(10)
        }
        return Response.success("查找成功", paperList.map { it.toDTO() })
    }

    override fun showMember(institutionId: Long): Response<List<UserDTO>> {
        log.info("查询机构成员")
        var memberList = institutionRepository.findMember(institutionId)
        if(memberList.size > 10){
            memberList = memberList.take(10)
        }
        return Response.success("查找成功", memberList.map { it.toDTO() })
    }
}