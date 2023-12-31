package com.example.springbatch.jobs

import com.example.springbatch.domain.Person
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.atomic.AtomicBoolean

@Configuration
@EnableBatchProcessing
open class BatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    open fun itemReader(): ItemReader<String> {
        // Example: In-memory reader. Replace this with your actual reader (JdbcCursorItemReader, FlatFileItemReader, etc.)
        val items = listOf("item1", "item2", "item3")
        return ListItemReader(items)
    }

    @Bean
    open fun itemProcessor(): ItemProcessor<String, String> {
        return ItemProcessor { item -> "Processed: $item" }
    }

    @Bean
    open fun itemWriter(): ItemWriter<String> {
        return ItemWriter { items -> items.forEach { println(it) } }
    }

    @Bean
    open fun chunkStep(): Step {
        return stepBuilderFactory.get("chunkStep")
            .chunk<String, String>(2) // Processing 2 items at a time
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build()
    }

    @Bean
    open fun sampleTasklet(): SimpleTasklet {
        return SimpleTasklet()
    }

    @Bean
    open fun sampleStep(): Step{
        return stepBuilderFactory.get("sampleStep")
            .tasklet(sampleTasklet())
            .build()
    }

    @Bean
    open fun job(jobBuilderFactory: JobBuilderFactory): Job {
        return jobBuilderFactory["combinedJob"]
            .incrementer(RunIdIncrementer())
            .start(chunkStep())  // Start with chunkStep
            .next(sampleStep())  // Then proceed to sampleStep
            .build()
    }

}