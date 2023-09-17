package com.example.springbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@EnableBatchProcessing
open class MyHomeBatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    @Bean
    open fun weeklyRefreshJob(): Job {
        return jobBuilderFactory.get("weeklyRefreshJob")
            .start(flow1()) // flow로 여러 step을 병렬 또는 순차적으로 시작
            .end()
            .build()
    }

    @Bean
    open fun flow1(): Flow {
        return FlowBuilder<Flow>("flow1")
            .start(createStep("aptStep", "https://myhome-apply.vercel.app/api/refresh?category=APT"))
            .next(createStep("nonAptStep", "https://myhome-apply.vercel.app/api/refresh?category=NonApt"))
            .next(createStep("Remain", "https://myhome-apply.vercel.app/api/refresh?category=Remain"))
            .build()
    }

    private fun createStep(stepName: String, url: String): Step {
        return stepBuilderFactory[stepName]
            .tasklet { _, _ ->
                val restTemplate = RestTemplate()
                val result = restTemplate.getForObject(url, String::class.java)
                println("$stepName Result: $result")
                null
            }
            .build()
    }




}