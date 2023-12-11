package com.example.newsapp.Model

data class NewsDetails(
    val sources: List<Source>
)

data class Source(
    val category: String,
    val country: String,
    val description: String,
    val id: String,
    val language: String,
    val name: String,
    val url: String
)