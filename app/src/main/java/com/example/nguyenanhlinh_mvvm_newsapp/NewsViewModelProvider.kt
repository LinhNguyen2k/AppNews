package com.example.nguyenanhlinh_mvvm_newsapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmnewsapp.repository.NewsRepository

class NewsViewModelProvider(
    val app: Application,
    val newsRepository: NewsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
    }
}