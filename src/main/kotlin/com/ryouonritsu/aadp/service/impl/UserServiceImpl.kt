package com.ryouonritsu.aadp.service.impl

import com.ryouonritsu.aadp.common.constants.AADPConstant
import com.ryouonritsu.aadp.domain.dto.PaperAuthorsDTO
import com.ryouonritsu.aadp.domain.dto.QueryCooperatorsResultDTO
import com.ryouonritsu.aadp.domain.dto.SimpleUserDTO
import com.ryouonritsu.aadp.domain.dto.UserDTO
import com.ryouonritsu.aadp.domain.protocol.request.ModifyUserInfoRequest
import com.ryouonritsu.aadp.domain.protocol.response.AcademicInformationResponse
import com.ryouonritsu.aadp.domain.protocol.response.QueryPapersResponse
import com.ryouonritsu.aadp.domain.protocol.response.QueryResearchesResponse
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.entity.Institution
import com.ryouonritsu.aadp.entity.User
import com.ryouonritsu.aadp.entity.UserFile
import com.ryouonritsu.aadp.repository.*
import com.ryouonritsu.aadp.service.UserService
import com.ryouonritsu.aadp.utils.RedisUtils
import com.ryouonritsu.aadp.utils.RequestContext
import com.ryouonritsu.aadp.utils.TokenUtils
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.io.path.Path

/**
 * @author ryouonritsu
 */
@Service
class UserServiceImpl(
    private val redisUtils: RedisUtils,
    private val userRepository: UserRepository,
    private val userFileRepository: UserFileRepository,
    private val institutionRepository: InstitutionRepository,
    private val paperRepository: PaperRepository,
    private val researchRepository: ResearchRepository,
    @Value("\${static.file.prefix}")
    private val staticFilePrefix: String
) : UserService {
    companion object {
        private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }

    private fun getHtml(url: String): Pair<Int, String?> {
        val client = OkHttpClient()
        val request = Request.Builder().get().url(url).build()
        return try {
            val response = client.newCall(request).execute()
            when (response.code) {
                200 -> Pair(200, response.body?.string())
                else -> Pair(response.code, null)
            }
        } catch (e: Exception) {
            Pair(500, e.message)
        }
    }

    private fun sendEmail(email: String, subject: String, html: String): Boolean {
        val account = "inkbook_ritsu@163.com"
        val password = "GMIRTTQDLBMWOROX"
        val nick = "ResearchOcean Official"
        val props = mapOf(
            "mail.smtp.auth" to "true",
            "mail.smtp.host" to "smtp.163.com",
            "mail.smtp.port" to "25"
        )
        val properties = Properties().apply { putAll(props) }
        val authenticator = object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(account, password)
            }
        }
        val mailSession = Session.getInstance(properties, authenticator)
        val htmlMessage = MimeMessage(mailSession).apply {
            setFrom(InternetAddress(account, nick, "UTF-8"))
            setRecipient(MimeMessage.RecipientType.TO, InternetAddress(email, "", "UTF-8"))
            setSubject(subject, "UTF-8")
            setContent(html, "text/html; charset=UTF-8")
        }
        log.info("Sending email to $email")
        return runCatching { Transport.send(htmlMessage) }.isSuccess
    }

    private fun check(
        email: String,
        username: String,
        password: String,
        realName: String
    ): Pair<Boolean, Response<Unit>?> {
        if (!email.matches(Regex("[\\w\\\\.]+@[\\w\\\\.]+\\.\\w+"))) return Pair(
            false, Response.failure("?????????????????????")
        )
        if (username.length > 50) return Pair(
            false, Response.failure("???????????????????????????50")
        )
        if (password.length < 8 || password.length > 30) return Pair(
            false, Response.failure("?????????????????????8-30??????")
        )
        if (realName.length > 50) return Pair(
            false, Response.failure("??????????????????????????????50")
        )
        return Pair(true, null)
    }

    private fun emailCheck(email: String?): Pair<Boolean, Response<Unit>?> {
        if (email.isNullOrBlank()) return Pair(
            false, Response.failure("??????????????????")
        )
        if (!email.matches(Regex("[\\w\\\\.]+@[\\w\\\\.]+\\.\\w+"))) return Pair(
            false, Response.failure("?????????????????????")
        )
        return Pair(true, null)
    }

    private fun sendVerifyCodeEmailUseTemplate(
        template: String,
        verificationCode: String,
        email: String,
        subject: String
    ): Response<Unit> {
        // ?????????????????????????????????!!!
//        val (code, html) = getHtml("http://101.42.171.88:8090/registration_verification?verification_code=$verification_code")
        val (code, html) = getHtml("http://localhost:8090/$template?verification_code=$verificationCode")
        val success = if (code == 200 && html != null) sendEmail(email, subject, html) else false
        return if (success) {
            redisUtils.set("verification_code", verificationCode, 5, TimeUnit.MINUTES)
            redisUtils.set("email", email, 5, TimeUnit.MINUTES)
            Response.success("??????????????????")
        } else Response.failure("?????????????????????")
    }

    override fun sendRegistrationVerificationCode(
        email: String?,
        modify: Boolean
    ): Response<Unit> {
        val (result, message) = emailCheck(email)
        if (!result && message != null) return message
        val t = userRepository.findByEmail(email!!)
        if (t != null) return Response.failure("?????????????????????")
        val subject = if (modify) "ResearchOcean?????????????????????" else "ResearchOcean?????????????????????"
        val verificationCode = (1..6).joinToString("") { "${(0..9).random()}" }
        return sendVerifyCodeEmailUseTemplate(
            "registration_verification",
            verificationCode,
            email,
            subject
        )
    }

    private fun verifyCodeCheck(verifyCode: String?): Pair<Boolean, Response<Unit>?> {
        val vc = redisUtils["verification_code"]
        if (vc.isNullOrBlank()) return Pair(
            false, Response.failure("???????????????")
        )
        if (verifyCode != vc) return Pair(
            false, Response.failure("???????????????, ???????????????")
        )
        redisUtils - "verification_code"
        return Pair(true, null)
    }

    override fun register(
        email: String?,
        verificationCode: String?,
        username: String?,
        password1: String?,
        password2: String?,
        avatar: String,
        realName: String
    ): Response<Unit> {
        if (email.isNullOrBlank()) return Response.failure("??????????????????")
        if (verificationCode.isNullOrBlank()) return Response.failure("?????????????????????")
        if (username.isNullOrBlank()) return Response.failure("?????????????????????")
        if (password1.isNullOrBlank()) return Response.failure("??????????????????")
        if (password2.isNullOrBlank()) return Response.failure("????????????????????????")
        val (result, message) = check(email, username, password1, realName)
        if (!result && message != null) return message
        val t = userRepository.findByEmail(email)
        if (t != null) return Response.failure("?????????????????????")
        return runCatching {
            val (re, msg) = verifyCodeCheck(verificationCode)
            if (!re && msg != null) return@runCatching msg
            if (redisUtils["email"] != email) return Response.failure("?????????????????????????????????")
            val temp = userRepository.findByUsername(username)
            if (temp != null) return Response.failure("??????????????????")
            if (password1 != password2) return Response.failure("??????????????????????????????")
            userRepository.save(
                User(
                    email = email,
                    username = username,
                    password = password1,
                    realName = realName,
                    avatar = avatar
                )
            )
            Response.success("????????????")
        }.onFailure { log.error(it.stackTraceToString()) }
            .getOrDefault(Response.failure("????????????, ??????????????????"))
    }

    override fun login(
        username: String?,
        password: String?,
        keepLogin: Boolean
    ): Response<List<Map<String, String>>> {
        if (username.isNullOrBlank()) return Response.failure("?????????????????????")
        if (password.isNullOrBlank()) return Response.failure("??????????????????")
        return runCatching {
            val user =
                userRepository.findByUsername(username) ?: return Response.failure("???????????????")
            if (user.password != password) return Response.failure("????????????")
            val token = TokenUtils.sign(user)
            if (keepLogin) redisUtils["${user.id}"] = token
            else redisUtils.set("${user.id}", token, 3, TimeUnit.DAYS)
            Response.success(
                "????????????", listOf(
                    mapOf(
                        "token" to token,
                        "user_id" to "${user.id}"
                    )
                )
            )
        }.onFailure { log.error(it.stackTraceToString()) }
            .getOrDefault(Response.failure("????????????, ??????????????????"))
    }

    override fun showInfo(userId: Long): Response<List<UserDTO>> {
        return runCatching {
            val user = userRepository.findById(userId).get()
            Response.success("????????????", listOf(user.toDTO()))
        }.onFailure {
            if (it is NoSuchElementException) {
                redisUtils - "$userId"
                return Response.failure("???????????????????????????, ??????????????????")
            }
            log.error(it.stackTraceToString())
        }.getOrDefault(
            Response.failure("????????????, ??????????????????")
        )
    }

    override fun selectUserByUserId(userId: Long): Response<List<UserDTO>> {
        return runCatching {
            val user = userRepository.findById(userId).get()
            Response.success("????????????", listOf(user.toDTO()))
        }.onFailure {
            if (it is NoSuchElementException) return Response.failure("???????????????????????????")
            log.error(it.stackTraceToString())
        }.getOrDefault(Response.failure("????????????, ??????????????????"))
    }

    override fun sendForgotPasswordEmail(email: String?): Response<Unit> {
        val (result, message) = emailCheck(email)
        if (!result && message != null) return message
        userRepository.findByEmail(email!!) ?: return Response.failure("?????????????????????")
        val subject = "ResearchOcean???????????????????????????"
        val verificationCode = (1..6).joinToString("") { "${(0..9).random()}" }
        return sendVerifyCodeEmailUseTemplate(
            "forgot_password",
            verificationCode,
            email,
            subject
        )
    }

    override fun changePassword(
        mode: Int?,
        oldPassword: String?,
        password1: String?,
        password2: String?,
        email: String?,
        verifyCode: String?
    ): Response<Unit> {
        when (mode) {
            0 -> {
                val (result, message) = verifyCodeCheck(verifyCode)
                if (!result && message != null) return message
                if (password1.isNullOrBlank() || password2.isNullOrBlank()) return Response.failure(
                    "??????????????????"
                )
                if (password1 != password2) return Response.failure("?????????????????????")
                val (re, msg) = emailCheck(email)
                if (!re && msg != null) return msg
                return runCatching {
                    val user = userRepository.findByEmail(email!!)
                        ?: return Response.failure("?????????????????????, ??????????????????, ??????????????????")
                    user.password = password1
                    userRepository.save(user)
                    redisUtils - "${user.id}"
                    Response.success<Unit>("????????????")
                }.onFailure { log.error(it.stackTraceToString()) }
                    .getOrDefault(Response.failure("????????????, ??????????????????"))
            }

            1 -> {
                return runCatching {
                    val user = userRepository.findById(RequestContext.userId.get()!!).get()
                    if (password1.isNullOrBlank() || password2.isNullOrBlank() || oldPassword.isNullOrBlank()) return Response.failure(
                        "??????????????????"
                    )
                    if (user.password != oldPassword) return Response.failure("???????????????")
                    if (password1.length < 8 || password1.length > 30) return Response.failure("?????????????????????8-30?????????")
                    if (password1 != password2) return Response.failure("?????????????????????")
                    user.password = password1
                    userRepository.save(user)
                    redisUtils - "${user.id}"
                    Response.success<Unit>("????????????")
                }.onFailure {
                    if (it is NoSuchElementException) {
                        redisUtils - "${RequestContext.userId.get()}"
                        return Response.failure("???????????????????????????????????????token????????????, ??????????????????")
                    }
                    log.error(it.stackTraceToString())
                }.getOrDefault(
                    Response.failure("????????????, ??????????????????")
                )
            }

            else -> return Response.failure("?????????????????????????????????, ??????0???1")
        }
    }

    override fun uploadFile(
        file: MultipartFile
    ): Response<List<Map<String, String>>> {
        return runCatching {
            if (file.size >= 10 * 1024 * 1024) return Response.failure("????????????, ??????????????????????????????10MB???")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS_")
            val time = LocalDateTime.now().format(formatter)
            val userId = RequestContext.userId.get()
            val fileDir = "static/file/${userId}"
            val fileName = time + file.originalFilename
            val filePath = "$fileDir/$fileName"
            if (!File(fileDir).exists()) File(fileDir).mkdirs()
            file.transferTo(Path(filePath))
            val fileUrl = "http://$staticFilePrefix:8090/file/${userId}/${fileName}"
            userFileRepository.save(
                UserFile(
                    url = fileUrl,
                    filePath = filePath,
                    fileName = fileName,
                    user = userRepository.findById(userId!!).get()
                )
            )
            Response.success(
                "????????????", listOf(
                    mapOf(
                        "url" to fileUrl
                    )
                )
            )
        }.onFailure { log.error(it.stackTraceToString()) }
            .getOrDefault(Response.failure("????????????, ??????????????????"))
    }

    override fun deleteFile(url: String): Response<Unit> {
        return try {
            val file = userFileRepository.findByUrl(url) ?: return Response.failure(
                "???????????????"
            )
            File(file.filePath).delete()
            userFileRepository.delete(file)
            Response.failure("????????????")
        } catch (e: Exception) {
            log.error(e.stackTraceToString())
            Response.failure("????????????, ??????????????????")
        }
    }

    override fun modifyUserInfo(request: ModifyUserInfoRequest): Response<Unit> {
        return runCatching {
            val user = userRepository.findById(RequestContext.userId.get()!!).get()
            if (!request.username.isNullOrBlank()) {
                val t = userRepository.findByUsername(request.username)
                if (t != null) return Response.failure("??????????????????")
                if (request.username.length > 50) return Response.failure("???????????????????????????50")
                user.username = request.username
            }
            if (!request.realName.isNullOrBlank()) {
                if (request.realName.length > 50) return Response.failure("??????????????????????????????50")
                user.realName = request.realName
            }
            if (!request.avatar.isNullOrBlank()) {
                user.avatar = request.avatar
            }
            if (request.isCertified != null) {
                user.isCertified = request.isCertified
            }
            if (!request.educationalBackground.isNullOrBlank()) {
                user.educationalBackground = request.educationalBackground
            }
            if (request.isAdmin != null) {
                user.isAdmin = request.isAdmin
            }
            if (request.institutionId != null) {
                user.institution = institutionRepository.findById(request.institutionId).get()
            }
            userRepository.save(user)
            Response.success<Unit>("????????????")
        }.onFailure {
            if (it is NoSuchElementException) {
                redisUtils - "${RequestContext.userId.get()}"
                return Response.failure("???????????????????????????????????????token????????????, ??????????????????")
            }
            log.error(it.stackTraceToString())
        }.getOrDefault(Response.failure("????????????, ??????????????????"))
    }

    override fun modifyEmail(
        email: String?,
        verifyCode: String?,
        password: String?
    ): Response<Unit> {
        return runCatching {
            val user = userRepository.findById(RequestContext.userId.get()!!).get()
            val (result, message) = emailCheck(email)
            if (!result && message != null) return message
            val t = userRepository.findByEmail(email!!)
            if (t != null) return Response.failure("?????????????????????")
            val (re, msg) = verifyCodeCheck(verifyCode)
            if (!re && msg != null) return@runCatching msg
            if (redisUtils["email"] != email) return Response.failure("?????????????????????????????????")
            if (password != user.password) return Response.failure("????????????")
            val (code, html) = getHtml("http://localhost:8090/change_email?email=${email}")
            val success =
                if (code == 200 && html != null) sendEmail(
                    user.email,
                    "ResearchOcean??????????????????",
                    html
                ) else false
            if (!success) throw Exception("??????????????????")
            user.email = email
            userRepository.save(user)
            Response.success("????????????")
        }.onFailure {
            if (it is NoSuchElementException) {
                redisUtils - "${RequestContext.userId.get()}"
                return Response.failure("???????????????????????????????????????token????????????, ??????????????????")
            }
            if (it.message != null) return Response.failure("${it.message}")
            else log.error(it.stackTraceToString())
        }.getOrDefault(Response.failure("????????????, ??????????????????"))
    }

    override fun adjustmentCredit(userId: Long, value: Int): Response<UserDTO> {
        log.info("adjustmentCredit: userId = $userId, value = $value")
        var user = try {
            userRepository.findById(userId).get()
        } catch (e: NoSuchElementException) {
            return Response.failure("???????????????????????????")
        }
        user.credit += value
        user = userRepository.save(user)
        return Response.success("????????????", user.toDTO())
    }

    override fun getAcademicInformation(userId: Long) = AcademicInformationResponse(
        academicCount = "${paperRepository.countByPaperAuthorId(userId)}",
        researchCount = "${researchRepository.countByResearchUserId(userId)}",
        totalCitations = "${paperRepository.sumPaperCitedByPaperAuthorId(userId)}"
    )

    override fun queryPapers(userId: Long, page: Int, limit: Int): QueryPapersResponse {
        val pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "paperCited")
        val papers = paperRepository.findByPaperAuthorId(userId, pageable)
        return QueryPapersResponse(
            papers.content.map { it.toDTO() },
            "${papers.totalElements}"
        )
    }

    override fun queryResearches(userId: Long, page: Int, limit: Int): QueryResearchesResponse {
        val pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "updateTime")
        val researches =
            researchRepository.findByResearchUserId(userId, pageable)
        return QueryResearchesResponse(
            researches.content.map { it.toDTO() },
            "${researches.totalElements}"
        )
    }

    override fun queryCooperators(userId: Long): List<QueryCooperatorsResultDTO> {
        val papers = paperRepository.findByPaperAuthorId(userId)
        val cooperatorMap = mutableMapOf<PaperAuthorsDTO, Long>()
        papers.forEach {
            it.toDTO().paperOtherAuthors.otherAuthors.forEach { author ->
                cooperatorMap.compute(author) { _, v -> v?.plus(1) ?: 1 }
            }
        }
        return cooperatorMap.toList().sortedByDescending { it.second }
            .map {
                val user = userRepository.findByUsernameOrRealNameLike(it.first.author)
                val institution = institutionRepository.findByInstitutionNameLike(it.first.unit)
                QueryCooperatorsResultDTO(
                    SimpleUserDTO(
                        userId = if (user.isEmpty) "${AADPConstant.LONG_MINUS_1}" else "${user.content[0].id}",
                        username = it.first.author,
                        institutionId = if (institution.isEmpty) "${AADPConstant.LONG_MINUS_1}" else "${institution.content[0].id}",
                        institutionName = it.first.unit
                    ), "${it.second}"
                )
            }
    }

    override fun claim(institutionName: String, user: Long): Int {
        log.info("??????????????????")
        var u = userRepository.findById(user).get()
        var r = institutionRepository.findByInstitutionName(institutionName).elementAtOrNull(0)
        if (r != null) {
            r.institutionCreator = u
            //???????????????
            return 1
        } else {
            institutionRepository.save(
                Institution(
                    institutionName = institutionName,
                    institutionCreator = u,
                    institutionImage = "",
                    institutionInfo = ""
                )
            )
        }
        return 0
    }
}