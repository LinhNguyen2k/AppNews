package com.example.nguyenanhlinh_mvvm_newsapp.model

data class NewsResponse(
    val articles:  MutableList<Article>,
    val status: String,
    val totalResults: Int
)