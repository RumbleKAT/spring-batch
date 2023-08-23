package com.example.springbatch.reader

import com.example.springbatch.domain.ApiResponse
import org.springframework.batch.item.ItemReader
import org.springframework.web.reactive.function.client.WebClient

class ApiReader(private val webClient:WebClient): ItemReader<ApiResponse> {
    override fun read(): ApiResponse? {
        return webClient.get()
            .uri("/todos/1")
            .retrieve()
            .bodyToMono(ApiResponse::class.java)
            .block()
    }
}