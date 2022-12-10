package com.ryouonritsu.aadp.controller

import com.ryouonritsu.aadp.common.annotation.AuthCheck
import com.ryouonritsu.aadp.domain.protocol.request.*
import com.ryouonritsu.aadp.domain.protocol.response.Response
import com.ryouonritsu.aadp.service.UserService
import com.ryouonritsu.aadp.utils.RedisUtils
import com.ryouonritsu.aadp.utils.TokenUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

/**
 * @author ryouonritsu
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
class UserController(
    private val userService: UserService,
    private val redisUtils: RedisUtils
) {
    @PostMapping("/sendRegistrationVerificationCode")
    @Tag(name = "用户接口")
    @Operation(
        summary = "发送注册验证码",
        description = "发送注册验证码到指定邮箱, 若modify为true, 则发送修改邮箱验证码, 默认为false"
    )
    fun sendRegistrationVerificationCode(@RequestBody request: SendRegistrationVerificationCodeRequest) =
        userService.sendRegistrationVerificationCode(request.email, request.modify)

    @PostMapping("/register")
    @Tag(name = "用户接口")
    @Operation(summary = "用户注册", description = "除了真实姓名其余必填")
    fun register(@RequestBody request: RegisterRequest) = userService.register(
        request.email,
        request.verificationCode,
        request.username,
        request.password1,
        request.password2,
        request.avatar,
        request.realName
    )

    @PostMapping("/login")
    @Tag(name = "用户接口")
    @Operation(
        summary = "用户登录",
        description = "keep_login为true时, 保持登录状态, 否则token会在3天后失效, 默认为false"
    )
    fun login(@RequestBody request: LoginRequest) =
        userService.login(request.username, request.password, request.keepLogin)

    @GetMapping("/logout")
    @AuthCheck
    @Tag(name = "用户接口")
    @Operation(summary = "用户登出")
    fun logout(
        @RequestParam("token") @Parameter(
            description = "用户登陆后获取的token令牌",
            required = true
        ) token: String
    ): Response<Any> {
        redisUtils - "${TokenUtils.verify(token).second}"
        return Response.success("登出成功")
    }

    @GetMapping("/showInfo")
    @AuthCheck
    @Tag(name = "用户接口")
    @Operation(summary = "返回已登陆用户的信息", description = "需要用户登陆才能查询成功")
    fun showInfo(
        @RequestParam("token") @Parameter(
            description = "用户登陆后获取的token令牌",
            required = true
        ) token: String
    ) = userService.showInfo(token)

    @GetMapping("/selectUserByUserId")
    @Tag(name = "用户接口")
    @Operation(summary = "根据用户id查询用户信息")
    fun selectUserByUserId(
        @RequestParam("user_id") @Parameter(
            description = "用户id",
            required = true
        ) userId: Long
    ) = userService.selectUserByUserId(userId)

    @PostMapping("/sendForgotPasswordEmail")
    @Tag(name = "用户接口")
    @Operation(summary = "发送找回密码验证码", description = "发送找回密码验证码到指定邮箱")
    fun sendForgotPasswordEmail(@RequestBody request: SendForgotPasswordEmailRequest) =
        userService.sendForgotPasswordEmail(request.email)

    @PostMapping("/changePassword")
    @AuthCheck
    @Tag(name = "用户接口")
    @Operation(
        summary = "修改用户密码",
        description = "可选忘记密码修改或正常修改密码, 参数的必要性根据模式选择, 如\"1: 验证码\"则表示模式1需要填写参数\"验证码\""
    )
    fun changePassword(@RequestBody request: ChangePasswordRequest) = userService.changePassword(
        request.mode,
        request.token,
        request.oldPassword,
        request.password1,
        request.password2,
        request.email,
        request.verifyCode
    )

    @PostMapping("/uploadFile")
    @AuthCheck
    @Tag(name = "用户接口")
    @Operation(
        summary = "上传文件",
        description = "将用户上传的文件保存在静态文件目录static/file/\${user_id}/\${file_name}下"
    )
    fun uploadFile(@RequestBody request: UploadFileRequest) =
        userService.uploadFile(request.file, request.token)

    @PostMapping("/deleteFile")
    @AuthCheck
    @Tag(name = "用户接口")
    @Operation(
        summary = "删除文件",
        description = "删除用户上传的文件, 使分享链接失效"
    )
    fun deleteFile(@RequestBody request: DeleteFileRequest) =
        userService.deleteFile(request.token, request.url)

    @PostMapping("/modifyUserInfo")
    @AuthCheck
    @Tag(name = "用户接口")
    @Operation(
        summary = "修改用户信息",
        description = "未填写的信息则保持原样不变"
    )
    fun modifyUserInfo(@RequestBody request: ModifyUserInfoRequest) =
        userService.modifyUserInfo(request)

    @PostMapping("/modifyEmail")
    @AuthCheck
    @Tag(name = "用户接口")
    @Operation(
        summary = "修改邮箱",
        description = "需要进行新邮箱验证和密码验证, 新邮箱验证发送验证码使用注册验证码接口即可"
    )
    fun modifyEmail(@RequestBody request: ModifyEmailRequest) =
        userService.modifyEmail(request.token, request.email, request.verifyCode, request.password)

    @PostMapping("/adjustmentCredit")
    @Tag(name = "用户接口")
    @Operation(
        summary = "调整用户积分",
        description = "根据用户id来调整该用户的积分, 最终结果再现有积分上增加\$value值"
    )
    fun adjustmentCredit(@Valid @RequestBody request: AdjustmentCreditRequest) =
        userService.adjustmentCredit(request.userId!!, request.value!!)
}