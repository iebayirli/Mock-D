package com.iebayirli.mockd.data.social

import com.squareup.moshi.Json
import java.io.Serializable

data class Social(
    @Json(name = "likeCount")
    val likeCount: Int,
    @Json(name = "commentCounts")
    val commentCounts: CommentCounts
) : Serializable
