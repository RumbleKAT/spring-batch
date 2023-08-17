package com.example.springbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
open class ScheduledTasks(
    private val jobLauncher: JobLauncher,
    private val yourJob: Job,
    @Value("\${schedule.rate}") private val scheduleRate: Long
) {
    @Scheduled(fixedRateString = "\${schedule.rate}")
    open fun runJob() {
        val jobParameters = JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters()

        jobLauncher.run(yourJob, jobParameters)
    }
}