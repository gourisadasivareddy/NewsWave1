package com.example.newsapp.Utils

import com.example.newsapp.Model.Source
import com.example.newswave.model.Article
import com.example.newswave.model.NewsApiResponse

sealed class ApiState {

    class Success(val newsData: NewsApiResponse) : ApiState()
//    class Success(val newsData: List<Source>) : ApiState()
    class Failure(val throwable: Throwable) : ApiState()
    object Loading: ApiState()
    object Empty: ApiState()
}
