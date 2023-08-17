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
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.transform.FieldSet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
@EnableBatchProcessing
open class BatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    open fun reader(): FlatFileItemReader<Person> {
        val reader = FlatFileItemReader<Person>()
        reader.setResource(ClassPathResource("sample-data.csv"))
        reader.setLinesToSkip(1)
        reader.setLineMapper(DefaultLineMapper<Person>().apply {
            setLineTokenizer(DelimitedLineTokenizer().apply {
                setNames("firstName", "lastName")
            })
            setFieldSetMapper { fields: FieldSet ->
                Person(
                    firstName = fields.readString("firstName"),
                    lastName = fields.readString("lastName")
                )
            }
        })
        return reader
    }

    @Bean
    open fun process(): ItemProcessor<Person, Person> = ItemProcessor { item ->
        item
    }

    @Bean
    open fun write(): ItemWriter<Person> = ItemWriter { items ->
        items.forEach { println(it) }
    }

//    @Bean
//    open fun step(stepBuilderFactory: StepBuilderFactory, reader: ItemReader<Person>, writer: ItemWriter<Person>, processor: ItemProcessor<Person, Person>): Step {
//        return stepBuilderFactory.get("step1")
//            .chunk<Person, Person>(10)
//            .reader(reader)
//            .processor(processor)
//            .writer(writer)
//            .build()
//    }
//
//    @Bean
//    open fun job(jobBuilderFactory: JobBuilderFactory, step: Step): Job {
//        return jobBuilderFactory["sampleJob"]
//            .incrementer(RunIdIncrementer())
//            .flow(step)
//            .end()
//            .build()
//    }
}
