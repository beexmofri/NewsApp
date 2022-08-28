package com.test.newsapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// single end point to get top headlines in the USA
interface APIServices {
    @GET("v2/top-headlines")

    fun getAllHeadlines(@Query("apiKey") apiKey:String="20247a61b6d92440e8b7153730bd92094",@Query("page") pageNumber:Int=1
                        ,@Query("country") country:String="us")
            :Call<NewsResponse>
}