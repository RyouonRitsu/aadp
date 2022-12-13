package com.ryouonritsu.aadp.config

import com.ryouonritsu.aadp.common.enums.ExceptionEnum
import com.ryouonritsu.aadp.common.exception.ServiceException
import com.ryouonritsu.aadp.domain.protocol.response.Response
import org.slf4j.LoggerFactory
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author ryouonritsu
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [ServiceException::class])
    fun serviceExceptionHandler(serviceException: ServiceException): Response<Unit> {
        log.error("ServiceException occurred: code = ${serviceException.code}, message = ${serviceException.message}")
        return if (serviceException.code != null) Response.failure(
            ExceptionEnum.getByCode(
                serviceException.code!!
            )
        )
        else Response.failure("${serviceException.message}")
    }

    @ExceptionHandler(value = [NullPointerException::class])
    fun exceptionHandler(exception: NullPointerException): Response<Unit> {
        log.error("NullPointerException occurred: $exception")
        return Response.failure(exception.toString())
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun exceptionHandler(exception: MethodArgumentNotValidException): Response<Map<String, String?>> {
        log.error("MethodArgumentNotValidException occurred: $exception")
        return Response.failure(ExceptionEnum.BAD_REQUEST, exception.message,
            exception.bindingResult.allErrors.associate { (it as FieldError).field to it.defaultMessage })
    }

    @ExceptionHandler(value = [Exception::class])
    fun exceptionHandler(exception: Exception): Response<Unit> {
        log.error("UnknownError occurred: $exception")
        return Response.failure(ExceptionEnum.INTERNAL_SERVER_ERROR, exception.toString())
    }
}