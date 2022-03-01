package com.example.nguyenanhlinh_mvvm_newsapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.example.nguyenanhlinh_mvvm_newsapp.NewsApplication
import com.example.nguyenanhlinh_mvvm_newsapp.model.NewsResponse
import com.example.nguyenanhlinh_mvvm_newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    app: Application,
    private val newsRepository: NewsRepository,
) : AndroidViewModel(app) {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsResponse: NewsResponse? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null
    var check = 0

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNewsCall(countryCode)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNewsCall(searchQuery)
    }

    private fun breakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun searchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun searchNewsCall(searchQuery: String) {
        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())
        try {
            if (checkInternet()) {
                val response = newsRepository.searchNews(searchQuery)
                searchNews.postValue(searchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet "))
            }
        } catch (t: Throwable) {
            searchNews.postValue(Resource.Error("Conversion Error"))

        }
    }

    private suspend fun breakingNewsCall(countryCode: String) {
        breakingNews.postValue(Resource.Loading())
        try {
            if (checkInternet()) {
                val response = newsRepository.getBreakingNews(countryCode)
                breakingNews.postValue(breakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No internet"))
            }
        } catch (t: Throwable) {
            breakingNews.postValue(Resource.Error("Conversion Error"))

        }
    }

    private fun checkInternet(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
//    fun isCheckStartActivity(): Boolean {
//        return when (check) {
//            2 -> true
//            1 -> {
//                check = 0
//                false
//            }
//            else -> false
//        }
//    }
//
//    fun setData() {
//        if (check == 0)
//            check = 2
//        else if (check == 2)
//            check = 0
//    }
}













