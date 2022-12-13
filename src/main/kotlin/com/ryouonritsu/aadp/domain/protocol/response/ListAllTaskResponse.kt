package com.ryouonritsu.aadp.domain.protocol.response

import com.ryouonritsu.aadp.domain.dto.ListAllTaskResultDTO

/**
 * @author ryouonritsu
 */
data class ListAllTaskResponse(
    val tasks: List<ListAllTaskResultDTO>,
    val total: Long
)
