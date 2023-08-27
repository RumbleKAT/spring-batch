package com.example.springbatch.jobs

import com.example.springbatch.domain.Person
import com.example.springbatch.domain.Todo
import com.example.springbatch.item.TodoItemProcessor
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import javax.persistence.EntityManagerFactory

@Configuration
@EnableBatchProcessing
open class TodoBatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    open fun restTemplate(): RestTemplate{
        return RestTemplate()
    }
    @Bean
    open fun todoJob(todoStep: Step): Job {
        return jobBuilderFactory["todoJob"]
            .start(todoStep)
            .build()
    }

    @Bean
    open fun todoStep(todoItemProcessor: TodoItemProcessor): Step {
        val todo = restTemplate().getForObject("https://jsonplaceholder.typicode.com/todos/1", Todo::class.java)!!
        return stepBuilderFactory.get("todoStep")
            .chunk<Todo, Todo>(10)
            .reader(ListItemReader(listOf(todo)))
            .processor(todoItemProcessor)
            .writer(todoItemWriter())
            .build()
    }

    @Bean
    open fun todoItemWriter(): ItemWriter<Todo> = ItemWriter { items ->
        items.forEach { println(it) }
    }

}