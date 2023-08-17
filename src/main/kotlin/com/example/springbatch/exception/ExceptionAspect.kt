package com.example.springbatch.exception

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
@Aspect
@Component
class ExceptionAspect {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @AfterThrowing(pointcut = "execution(* com.example.springbatch..*.*(..))", throwing = "exception")
    fun logAfterThrowing(joinPoint: JoinPoint, exception: Throwable){
        logger.error("Exception in ${joinPoint.signature.name} with cause = ${exception.cause}")
    }
}