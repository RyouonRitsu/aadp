package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.domain.dto.InstitutionDTO
import com.ryouonritsu.aadp.domain.dto.PaperDTO
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.User

interface InstitutionService {
    fun createInstitution(
        institutionName: String,
        institutionInfo: String,
        institutionImage: String,
        institutionId: Long,
        institutionCreator: User
    ): Response<Unit>

    //提供机构基本信息：图片 名称 成员数量单独列出
    fun showBasicInfo(institutionId: Long): Response<List<InstitutionDTO>>
    fun showMemberNum(institutionId: Long): Long
    //机构学术信息：发表的学术成果数 总被引数
    fun showPaperNum(institutionId: Long): Long
    fun showCitedNum(institutionId: Long): Long
    //机构发表的学术成果 点击跳转
    fun showPaper(institutionId: Long): Response<List<PaperDTO>>
    //机构成员：头像 姓名
    fun showMember(institutionId: Long): Response<List<UserDTO>>
}