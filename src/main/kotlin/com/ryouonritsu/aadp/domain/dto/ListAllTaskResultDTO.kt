package com.ryouonritsu.aadp.domain.dto

/**
 * @author ryouonritsu
 */
data class ListAllTaskResultDTO(
    val taskId: String,
    val operatorId: String,
    val operatorName: String,
    val institutionId: String,
    val institutionName: String,
    val paperId: String? = null,
    val paperTitle: String? = null
)
