package com.example.nguyenanhlinh_mvvm_newsapp.repository

import com.example.nguyenanhlinh_mvvm_newsapp.api.RetrofitInstance

class NewsRepository() {
    suspend fun getBreakingNews(countryCode: String) =
        RetrofitInstance.api.getBreakingNews(countryCode)

    suspend fun searchNews(searchQuery: String) =
        RetrofitInstance.api.searchForNews(searchQuery)
}