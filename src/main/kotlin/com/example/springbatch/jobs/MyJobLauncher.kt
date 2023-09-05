package com.example.springbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
open class MyJobLauncher(
    private val jobLauncher: JobLauncher,
    private val applicationContext: ApplicationContext
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val jobName = args.firstOrNull() ?: throw IllegalArgumentException("Job name must be provided")
        println("jobName::${jobName}")
        val job = applicationContext.getBean(jobName, Job::class.java)
            ?: throw IllegalArgumentException("No job found with the name: $jobName")

        val jobParameters = JobParametersBuilder()
            // Add parameters as needed
            .toJobParameters()

        jobLauncher.run(job, jobParameters)
    }
}