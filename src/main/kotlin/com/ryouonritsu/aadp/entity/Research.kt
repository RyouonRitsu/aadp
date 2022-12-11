package com.ryouonritsu.aadp.entity

import com.ryouonritsu.aadp.domain.dto.ResearchDTO
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.*


@Entity
class Research (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "research_title")
    var researchTitle: String = "",

    @Column(name = "research_content")
    var researchContent:String = "",

    @Column(name = "research_abstract")
    var researchAbstract: String = "",

    @Column(name = "publish_time")
    var publishTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_time")
    var updateTime: LocalDateTime = LocalDateTime.now(),

    @OneToOne(targetEntity = User::class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    var userList: List<User>? = null
    var user: User? =null
){
    fun toDTO() = ResearchDTO(
        id = "$id",
        researchTitle = researchTitle,
        researchAbstract = researchAbstract,
        researchContent = researchContent,
        publishTime = publishTime,
        updateTime = updateTime,
        userId = "$user?.id"
    )
}