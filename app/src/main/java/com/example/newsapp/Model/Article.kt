package com.example.newswave.model

data class Article(
    val author: String?=null,
    val content: String?=null,
    val description: String?=null,
    val publishedAt: String,
    val source: Source?=null,
    val title: String?=null,
    val url: String?=null,
    val urlToImage: String?=null
)