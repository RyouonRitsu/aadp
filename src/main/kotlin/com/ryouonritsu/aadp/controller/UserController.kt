package com.ryouonritsu.aadp.controller

import com.ryouonritsu.aadp.service.UserService
import com.ryouonritsu.aadp.utils.RedisUtils
import com.ryouonritsu.aadp.utils.TokenUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

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
    fun sendRegistrationVerificationCode(
        @RequestParam("email") @Parameter(description = "邮箱", required = true) email: String?,
        @RequestParam(
            "modify",
            defaultValue = "false"
        ) @Parameter(description = "是否修改邮箱") modify: Boolean
    ) = userService.sendRegistrationVerificationCode(email, modify)

    @PostMapping("/register")
    @Tag(name = "用户接口")
    @Operation(summary = "用户注册", description = "除了真实姓名其余必填")
    fun register(
        @RequestParam("email") @Parameter(description = "邮箱", required = true) email: String?,
        @RequestParam("verification_code") @Parameter(
            description = "验证码",
            required = true
        ) verificationCode: String?,
        @RequestParam("username") @Parameter(
            description = "用户名",
            required = true
        ) username: String?,
        @RequestParam("password1") @Parameter(
            description = "密码",
            required = true
        ) password1: String?,
        @RequestParam("password2") @Parameter(
            description = "确认密码",
            required = true
        ) password2: String?,
        @RequestParam(
            "avatar",
            defaultValue = ""
        ) @Parameter(description = "个人头像") avatar: String,
        @RequestParam(
            value = "real_name",
            defaultValue = ""
        ) @Parameter(description = "真实姓名") realName: String,
    ) = userService.register(
        email,
        verificationCode,
        username,
        password1,
        password2,
        avatar,
        realName
    )

    @PostMapping("/login")
    @Tag(name = "用户接口")
    @Operation(
        summary = "用户登录",
        description = "keep_login为true时, 保持登录状态, 否则token会在3天后失效, 默认为false"
    )
    fun login(
        @RequestParam("username") @Parameter(
            description = "用户名",
            required = true
        ) username: String?,
        @RequestParam("password") @Parameter(
            description = "密码",
            required = true
        ) password: String?,
        @RequestParam(
            "keep_login",
            defaultValue = "false"
        ) @Parameter(description = "是否记住登录") keepLogin: Boolean
    ) = userService.login(username, password, keepLogin)

    @GetMapping("/logout")
    @Tag(name = "用户接口")
    @Operation(summary = "用户登出")
    fun logout(
        @RequestParam("token") @Parameter(
            description = "用户登陆后获取的token令牌",
            required = true
        ) token: String
    ): Map<String, Any> {
        redisUtils - "${TokenUtils.verify(token).second}"
        return mapOf(
            "success" to true,
            "message" to "登出成功"
        )
    }

    @GetMapping("/showInfo")
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
    fun sendForgotPasswordEmail(
        @RequestParam("email") @Parameter(description = "邮箱", required = true) email: String?
    ) = userService.sendForgotPasswordEmail(email)

    @PostMapping("/changePassword")
    @Tag(name = "用户接口")
    @Operation(
        summary = "修改用户密码",
        description = "可选忘记密码修改或正常修改密码, 参数的必要性根据模式选择, 如\"1: 验证码\"则表示模式1需要填写参数\"验证码\""
    )
    fun changePassword(
        @RequestParam("mode") @Parameter(
            description = "修改模式, 0为忘记密码修改, 1为正常修改",
            required = true
        ) mode: Int?,
        @RequestParam(
            "token",
            defaultValue = ""
        ) @Parameter(description = "1: 用户登陆后获取的token令牌") token: String,
        @RequestParam(
            "oldPassword",
            required = false
        ) @Parameter(description = "1: 旧密码") oldPassword: String?,
        @RequestParam("password1") @Parameter(
            description = "新密码",
            required = true
        ) password1: String?,
        @RequestParam("password2") @Parameter(
            description = "确认新密码",
            required = true
        ) password2: String?,
        @RequestParam("email", required = false) @Parameter(
            description = "0: 邮箱",
            required = true
        ) email: String?,
        @RequestParam(
            "verify_code",
            required = false
        ) @Parameter(description = "0: 验证码") verifyCode: String?
    ) = userService.changePassword(
        mode,
        token,
        oldPassword,
        password1,
        password2,
        email,
        verifyCode
    )

    @PostMapping("/uploadFile")
    @Tag(name = "用户接口")
    @Operation(
        summary = "上传文件",
        description = "将用户上传的文件保存在静态文件目录static/file/\${user_id}/\${file_name}下"
    )
    fun uploadFile(
        @RequestParam("file") @Parameter(description = "文件", required = true) file: MultipartFile,
        @RequestParam("token") @Parameter(
            description = "用户认证令牌",
            required = true
        ) token: String,
    ) = userService.uploadFile(file, token)

    @PostMapping("/deleteFile")
    @Tag(name = "用户接口")
    @Operation(
        summary = "删除文件",
        description = "删除用户上传的文件, 使分享链接失效"
    )
    fun deleteFile(
        @RequestParam("token") @Parameter(
            description = "用户认证令牌",
            required = true
        ) token: String,
        @RequestParam("url") @Parameter(description = "文件分享链接", required = true) url: String
    ) = userService.deleteFile(token, url)

    @PostMapping("/modifyUserInfo")
    @Tag(name = "用户接口")
    @Operation(
        summary = "修改用户信息",
        description = "未填写的信息则保持原样不变"
    )
    fun modifyUserInfo(
        @RequestParam("token") @Parameter(
            description = "用户认证令牌",
            required = true
        ) token: String,
        @RequestParam(
            "username",
            defaultValue = ""
        ) @Parameter(description = "用户名", required = true) username: String?,
        @RequestParam(
            "real_name",
            defaultValue = ""
        ) @Parameter(description = "真实姓名", required = true) realName: String?,
        @RequestParam(
            "avatar",
            defaultValue = ""
        ) @Parameter(description = "个人头像", required = true) avatar: String?,
    ) = userService.modifyUserInfo(token, username, realName, avatar)

    @PostMapping("/modifyEmail")
    @Tag(name = "用户接口")
    @Operation(
        summary = "修改邮箱",
        description = "需要进行新邮箱验证和密码验证, 新邮箱验证发送验证码使用注册验证码接口即可"
    )
    fun modifyEmail(
        @RequestParam("token") @Parameter(
            description = "用户认证令牌",
            required = true
        ) token: String,
        @RequestParam("email") @Parameter(description = "新邮箱", required = true) email: String?,
        @RequestParam("verify_code") @Parameter(
            description = "邮箱验证码",
            required = true
        ) verifyCode: String?,
        @RequestParam("password") @Parameter(
            description = "密码",
            required = true
        ) password: String?
    ) = userService.modifyEmail(token, email, verifyCode, password)
}