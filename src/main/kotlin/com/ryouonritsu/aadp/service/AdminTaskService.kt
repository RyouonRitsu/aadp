package com.ryouonritsu.aadp.service

import com.ryouonritsu.aadp.common.enums.ObjectEnum
import com.ryouonritsu.aadp.domain.protocol.request.AddTaskRequest
import com.ryouonritsu.aadp.domain.protocol.request.TaskBatchOperationRequest
import com.ryouonritsu.aadp.domain.protocol.response.ListAllTaskResponse
import com.ryouonritsu.aadp.domain.protocol.response.Response

/**
 * @author ryouonritsu
 */
interface AdminTaskService {
    fun insert(request: AddTaskRequest): Response<Unit>
    fun listAll(type: ObjectEnum, page: Int, limit: Int): ListAllTaskResponse
    fun batchOperation(request: TaskBatchOperationRequest): Response<Unit>
}