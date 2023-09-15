package com.vhpg.practica2.data.remote

import com.google.gson.annotations.SerializedName

data class ProductDetailDto(
    @SerializedName("category")
    val category: Int,
    @SerializedName("cost")
    val cost: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("lastRestockDate")
    val lastRestockDate: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("url")
    val url: String
)