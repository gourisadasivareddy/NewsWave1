package com.example.newsapp.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Repository.NewsRepository
import com.example.newsapp.Utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {

    val responseData: MutableState<ApiState> = mutableStateOf(ApiState.Empty)
    val headlineResponseData: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    init {
//        getNews()
        getHeadLineNews()
    }

//    fun getNews(selectedCategory: String = "Business") = viewModelScope.launch {
//        newsRepository.fetchNewsData(newsCategory = selectedCategory)
//            .onStart {
//                responseData.value = ApiState.Loading
//            }.catch {
//                responseData.value = ApiState.Failure(it)
//            }.collect {
////                responseData.value = ApiState.Success(it.sources)
//            }
//    }

    fun getHeadLineNews(selectedCategory: String = "Business") = viewModelScope.launch {
        newsRepository.getTopHeadlines(newsCategory = selectedCategory)
            .onStart {
                headlineResponseData.value = ApiState.Loading
            }.catch {
                headlineResponseData.value = ApiState.Failure(it)
            }.collect {
                headlineResponseData.value = it.body()?.let { it1 -> ApiState.Success(it1) }!!
            }
    }
}