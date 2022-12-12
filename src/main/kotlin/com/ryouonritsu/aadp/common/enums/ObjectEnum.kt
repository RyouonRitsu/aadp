package com.ryouonritsu.aadp.common.enums

/**
 * @author ryouonritsu
 */
enum class ObjectEnum(
    val code: Int,
    val desc: String
) {
    PAPER(1, "论文"),
    INSTITUTION(2, "机构");

    fun valueOf(code: Int) = values().first { it.code == code }
}