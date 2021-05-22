package com.iebayirli.mockd.data.social

import com.squareup.moshi.Json
import java.io.Serializable

data class CommentCounts(
    @Json(name = "averageRating")
    val averageRating: Float,
    @Json(name = "anonymousCommentsCount")
    val anonymousCommentsCount: Int,
    @Json(name = "memberCommentsCount")
    val memberCommentsCount: Int
) : Serializable
