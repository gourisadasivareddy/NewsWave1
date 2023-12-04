package com.example.newsapp.Repository

import com.example.newsapp.RetroFit.APIService
import com.example.newsapp.Utils.NewsAPIHelper
import com.example.newsapp.Model.NewsDetails
import com.example.newswave.model.NewsApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


class NewsRepository @Inject constructor(private val apiService:APIService) {

//    fun fetchNewsData(newsCategory: String): Flow<NewsDetails> = flow {
//        kotlinx.coroutines.delay(1_000)
//        emit(apiService.fetchNewsData(apiKey = NewsAPIHelper.API_KEY, newsCategory = newsCategory))
//    }.flowOn(Dispatchers.IO)


    fun getTopHeadlines(newsCategory: String): Flow<Response<NewsApiResponse>> = flow {
        kotlinx.coroutines.delay(1_000)
        emit(apiService.getTopHeadlines("in",newsCategory,NewsAPIHelper.API_KEY))
    }.flowOn(Dispatchers.IO)
}