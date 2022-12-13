package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.domain.dto.InstitutionDTO
import com.ryouonritsu.aadp.domain.dto.PaperDTO
import java.util.ListResourceBundle
import javax.xml.ws.Response

interface InstitutionService {
    //提供机构基本信息：图片 名称 成员数量单独列出
    fun showBasicInfo(InstitutionId: Long): Response<List<InstitutionDTO>>
    fun showMemberNum(InstitutionId: Long): Long
    //机构学术信息：发表的学术成果数 总被引数
    fun showPaperNum(InstitutionId: Long): Long
    fun showCitedNum(InstitutionId: Long): Long
    //机构发表的学术成果 点击跳转
    fun showPaper(InstitutionId: Long): Response<List<PaperDTO>>
    //机构成员：头像 姓名
    fun showMember(InstitutionId: Long): Response<List<UserDTO>>
}