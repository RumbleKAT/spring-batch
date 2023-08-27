package com.example.springbatch.item

import com.example.springbatch.domain.Todo
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
open class TodoItemProcessor : ItemProcessor<Todo,Todo> {
    override fun process(item: Todo): Todo {
        println("${item.title} is processed")
        return item
    }
}