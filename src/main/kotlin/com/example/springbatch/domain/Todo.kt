package com.example.springbatch.domain

import org.springframework.data.annotation.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

data class Todo(
    val id: Long? = null,
    val userId: Int? = null,
    val title: String? = null,
    val completed: Boolean? = null)
