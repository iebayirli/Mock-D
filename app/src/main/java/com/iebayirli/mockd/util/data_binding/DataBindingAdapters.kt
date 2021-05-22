package com.iebayirli.mockd.util.data_binding

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.iebayirli.mockd.R
import com.iebayirli.mockd.data.product.Price
import com.iebayirli.mockd.data.social.CommentCounts


@SuppressLint("SetTextI18n")
@BindingAdapter("setProductValue")
fun setProductValue(textView: MaterialTextView, price: Price?) {
    price?.let {
        textView.text = it.value.toString() + " " + it.currency
    }
}

@BindingAdapter("setProductImage")
fun setProductImage(imageView: ShapeableImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(it)
            .into(imageView)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setCommentCounts")
fun setCommentCounts(textView: MaterialTextView, commentCounts: CommentCounts?) {
    commentCounts?.let {
        textView.text = "(${it.anonymousCommentsCount + it.memberCommentsCount} yorum)"
    }
}

@BindingAdapter("setLikeState")
fun setLikeState(imageView: ShapeableImageView, state: Boolean) {
    if (state) {
        val unwrappedDrawable =
            AppCompatResources.getDrawable(imageView.context, R.drawable.ic_favorite)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, Color.RED)
        imageView.setImageDrawable(wrappedDrawable)
    } else {
        imageView.setImageResource(R.drawable.ic_favorite_border)
    }
}

@BindingAdapter("setRefreshText")
fun setRefreshText(textView: MaterialTextView, counter: Int) {
    if (counter == 1) {
        textView.text = "Refreshing..."
    } else {
        textView.text = "$counter sn"
    }
}