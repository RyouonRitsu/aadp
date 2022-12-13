package com.ryouonritsu.aadp.service.impl

import com.alibaba.fastjson2.toJSONString
import com.ryouonritsu.aadp.common.enums.ExceptionEnum
import com.ryouonritsu.aadp.common.enums.ObjectEnum
import com.ryouonritsu.aadp.common.exception.ServiceException
import com.ryouonritsu.aadp.domain.dto.ListAllTaskResultDTO
import com.ryouonritsu.aadp.domain.protocol.request.AddTaskRequest
import com.ryouonritsu.aadp.domain.protocol.request.TaskBatchOperationRequest
import com.ryouonritsu.aadp.domain.protocol.response.ListAllTaskResponse
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.AdminTask
import com.ryouonritsu.aadp.repository.AdminTaskRepository
import com.ryouonritsu.aadp.repository.InstitutionRepository
import com.ryouonritsu.aadp.repository.PaperRepository
import com.ryouonritsu.aadp.repository.UserRepository
import com.ryouonritsu.aadp.service.AdminTaskService
import com.ryouonritsu.aadp.utils.RedisUtils
import com.ryouonritsu.aadp.utils.RequestContext
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * @author ryouonritsu
 */
@Service
class AdminTaskServiceImpl(
    private val redisUtils: RedisUtils,
    private val adminTaskRepository: AdminTaskRepository,
    private val paperRepository: PaperRepository,
    private val institutionRepository: InstitutionRepository,
    private val userRepository: UserRepository
) : AdminTaskService {
    companion object {
        private val log = LoggerFactory.getLogger(AdminTaskServiceImpl::class.java)
    }

    override fun insert(request: AddTaskRequest): Response<Unit> {
        val operator = userRepository.findById(RequestContext.userId.get()!!)
            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
        adminTaskRepository.save(
            AdminTask(
                operator = operator,
                objectId = request.objectId!!,
                objectType = request.objectType!!.code
            )
        )
        return Response.success("保存成功")
    }

    override fun listAll(type: ObjectEnum, page: Int, limit: Int): ListAllTaskResponse {
        val pageable = PageRequest.of(page - 1, limit)
        val tasks = adminTaskRepository.findAllByObjectType(type.code, pageable)
        return ListAllTaskResponse(
            tasks.content.map {
                log.info("process task: {}", it.toJSONString())
                when (ObjectEnum.valueOf(it.objectType)) {
                    ObjectEnum.INSTITUTION -> {
                        val institution = institutionRepository.findById(it.objectId)
                            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
                        ListAllTaskResultDTO(
                            taskId = "${it.id}",
                            operatorId = "${it.operator.id}",
                            operatorName = it.operator.username,
                            institutionId = "${institution.id}",
                            institutionName = institution.institutionName
                        )
                    }

                    ObjectEnum.PAPER -> {
                        val paper = paperRepository.findById(it.objectId)
                            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
                        ListAllTaskResultDTO(
                            taskId = "${it.id}",
                            operatorId = "${it.operator.id}",
                            operatorName = it.operator.username,
                            institutionId = "${it.operator.institution?.id}",
                            institutionName = "${it.operator.institution?.institutionName}",
                            paperId = "${paper.id}",
                            paperTitle = paper.paperTitle
                        )
                    }
                }
            }, "${tasks.totalElements}"
        )
    }

    override fun batchOperation(request: TaskBatchOperationRequest): Response<Unit> {
        val tasks = adminTaskRepository.findAllById(request.taskIds)
        tasks.forEach {
            it.isDeleted = true
            if (request.operationType!!) {
                when (ObjectEnum.valueOf(it.objectType)) {
                    ObjectEnum.INSTITUTION -> {
                        val institution = institutionRepository.findById(it.objectId)
                            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
                        it.operator.institution = institution
                        it.operator.isCertified = true
                        userRepository.save(it.operator)
                    }

                    ObjectEnum.PAPER -> {
                        val paper = paperRepository.findById(it.objectId)
                            .orElseThrow { ServiceException(ExceptionEnum.OBJECT_DOES_NOT_EXIST) }
                        paper.paperAuthorId = it.operator.id
                        paper.paperAuthor = it.operator.realName.ifBlank { it.operator.username }
                        paperRepository.save(paper)
                    }
                }
            }
            adminTaskRepository.save(it)
        }
        return Response.success("操作成功")
    }
}