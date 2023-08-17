package com.example.springbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
open class BatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    open fun logStep(): Step = stepBuilderFactory.get("logStep")
        .tasklet(logTasklet())
        .build()

    @Bean
    open fun logTasklet(): Tasklet = Tasklet { _, _ ->
        println("Executing logTasklet")
        RepeatStatus.FINISHED
    }

    @Bean
    open fun logJob(): Job = jobBuilderFactory.get("logJob")
        .start(logStep())
        .build()
}