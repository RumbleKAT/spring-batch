package com.example.springbatch.listener

import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.listener.StepExecutionListenerSupport
import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus

class StepLoggerListener : StepExecutionListenerSupport() {
    private val logger = LoggerFactory.getLogger(StepLoggerListener::class.java)

    override fun beforeStep(stepExecution: StepExecution) {
        logger.info("Step started: ${stepExecution.stepName}")
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        logger.info("Step finished: ${stepExecution.stepName} with status: ${stepExecution.exitStatus}")
        return null
    }
}