package com.example.springbatch.jobs

import com.example.springbatch.domain.Todo
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@EnableBatchProcessing
open class TodoBatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    var global_idx: Long = 1

    @Bean
    open fun restTemplate(): RestTemplate{
        return RestTemplate()
    }

    @Bean
    open fun todoJob(): Job {
        println("todo Job")
        return jobBuilderFactory["todoJob"]
            .start(todoStep(global_idx++))
            .build()
    }

    @Bean
    open fun todoItemProcessor(): ItemProcessor<Todo,Todo> = ItemProcessor { item ->
        println("${item.title} is processed")
        item
    }

    @Bean
    @StepScope
    open fun todoReader(@Value("#{jobParameters['idx']}") idx: Long?): ItemReader<Todo> {
        println(idx)
        val actualIdx = idx ?: 1L
        val todo = restTemplate().getForObject("https://jsonplaceholder.typicode.com/todos/${actualIdx}", Todo::class.java)!!
        return ListItemReader(listOf(todo))
    }

    @Bean
    open fun todoStep(idx:Long): Step {
        return stepBuilderFactory.get("todoStep")
            .chunk<Todo, Todo>(1)
            .reader(todoReader(null))
            .processor(todoItemProcessor())
            .writer(todoItemWriter())
            .build()
    }

    @Bean
    open fun todoItemWriter(): ItemWriter<Todo> = ItemWriter { items ->
        items.forEach { println(it) }
    }

}