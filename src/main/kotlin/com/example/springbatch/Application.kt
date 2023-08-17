package com.example.springbatch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringBatchKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchKotlinApplication>(*args)
}
