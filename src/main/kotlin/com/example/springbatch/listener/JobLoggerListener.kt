package com.example.springbatch.listener

import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class JobLoggerListener : JobExecutionListener{
    private val logger = LoggerFactory.getLogger(JobLoggerListener::class.java)
    override fun beforeJob(jobExecution: JobExecution) {
        logger.info("Job started: ${jobExecution.jobInstance.jobName}")
    }

    override fun afterJob(jobExecution: JobExecution) {
        logger.info("Job finished: ${jobExecution.jobInstance.jobName} with status: ${jobExecution.status}")
    }
}