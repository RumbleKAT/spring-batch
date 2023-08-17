package com.example.springbatch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class SpringBatchKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchKotlinApplication>(*args)
}
