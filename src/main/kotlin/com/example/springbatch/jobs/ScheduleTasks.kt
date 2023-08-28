package com.example.springbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Component
open class ScheduledTasks(
    private val jobLauncher: JobLauncher,
    @Autowired private val todoJob:Job
) {

    private var globalIdx = 1

    @Scheduled(cron = "0 * * * * ?")
    open fun runSpecificJob() {
        val params = JobParametersBuilder()
            .addLong("idx", globalIdx.toLong())
            .addDate("currentTime", Date())
            .toJobParameters()
        globalIdx++  // idx 값을 증가

        try {
            val jobExecution = jobLauncher.run(todoJob, params) // specify the job you want to run here
            println("Job execution status: ${jobExecution.status}")
        } catch (e: Exception) {
            println("Failed to execute job")
            e.printStackTrace()
        }
    }
}