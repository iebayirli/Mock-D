package com.iebayirli.mockd.data.product

import com.squareup.moshi.Json
import java.io.Serializable

data class Price(
    @Json(name = "value")
    val value: Double,
    @Json(name = "currency")
    val currency: String
) : Serializable
