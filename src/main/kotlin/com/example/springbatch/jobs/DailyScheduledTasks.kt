package com.example.springbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
open class DailyScheduledTasks(
    private val jobLauncher: JobLauncher,
    private val myDailyJob: Job
) {

    @Scheduled(cron = "0 0 12 * * ?") // 매일 정오에 실행
    fun runDailyBatch() {
        val jobParameters = JobParametersBuilder()
            .addLong("uniqueness", System.currentTimeMillis())
            .toJobParameters()

        jobLauncher.run(myDailyJob, jobParameters)
    }
}