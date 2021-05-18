package com.prueba.retrofit

import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("author") val author:String,
    @SerializedName("title") val title:String,
    @SerializedName("description") val description:String,
    @SerializedName("urlToImage") val urlToImage:String,
)
