package com.iebayirli.mockd.data.product

import com.squareup.moshi.Json
import java.io.Serializable

data class Product(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "price")
    val price: Price
) : Serializable
