package com.test.newsapp.data

import com.google.gson.annotations.SerializedName

// object within the NewsModel
data class SourceModel (
   @SerializedName("id") var id:String?,
   @SerializedName("name") var name:String?
)