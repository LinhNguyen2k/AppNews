package com.example.nguyenanhlinh_mvvm_newsapp.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nguyenanhlinh_mvvm_newsapp.repository.NewsRepository
import com.example.nguyenanhlinh_mvvm_newsapp.viewmodel.NewsViewModel

class NewsViewModelProvider(
    val app: Application,
    val newsRepository: NewsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
    }
}