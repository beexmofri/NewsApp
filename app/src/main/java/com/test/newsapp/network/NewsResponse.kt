package com.test.newsapp.network

import com.test.newsapp.data.NewsModel
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("totalResults") var totalResults:Int,
    @SerializedName("articles") var articles:MutableList<NewsModel>
)
