package com.iebayirli.mockd.scene.product_detail

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iebayirli.mockd.base.BaseViewModel
import com.iebayirli.mockd.data.product.Product
import com.iebayirli.mockd.data.social.CommentCounts
import com.iebayirli.mockd.data.social.Social
import com.iebayirli.mockd.repository.ProductRepository
import com.iebayirli.mockd.service.ILikeClickListener
import com.iebayirli.mockd.util.error_dialog.ErrorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel(), ILikeClickListener {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _socialInfo = MutableLiveData<Social>()
    val socialInfo: LiveData<Social> = _socialInfo

    private val _likeState = MutableLiveData<Boolean>(false)
    val likeState: LiveData<Boolean> = _likeState

    private val _likeCount = MutableLiveData<Int>()
    val likeCount: LiveData<Int> = _likeCount

    private val _refreshTimer = MutableLiveData<Int>(0)
    val refreshTimer: LiveData<Int> = _refreshTimer

    private val refreshSocialInfo by lazy {
        startCoroutineTimer(repeatMillis = REPEAT_TIME) {
            Log.i(TAG, "Fetched social info")
            fetchSocialInfo()
        }
    }

    private val timer = object : CountDownTimer(REPEAT_TIME, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val seconds = (millisUntilFinished / 1000).toInt()
            _refreshTimer.value = seconds
        }

        override fun onFinish() {
            //onFinish
        }
    }

    init {
        fetchProductInfo()
        fetchSocialInfo()
    }

    private fun fetchProductInfo() {
        viewModelScope.launch {
            apiCallWithFlow(
                request = productRepository.getProductInfo(),
                loading = { true },
                collect = { postProductInfo(it) },
                catch = {
                    handleError(
                        ErrorModel(
                            title = "Error",
                            message = it.message
                        )
                    )
                }
            )
        }
    }

    private fun postProductInfo(product: Product) {
        _product.value = product
    }

    private fun fetchSocialInfo() {
        viewModelScope.launch {
            apiCallWithFlow(
                request = productRepository.getSocialInfo(),
                loading = {
                    Log.i(TAG, "flow onStart : Timer cancelled")
                    timer.cancel()
                    false
                },
                collect = {
                    if (!refreshSocialInfo.isActive) {
                        refreshSocialInfo.start()
                    }
                    Log.i(TAG, "flow collect : Timer started")
                    timer.start()
                    postSocialInfo(it)
                },
                catch = {
                    handleError(
                        ErrorModel(
                            title = "Error",
                            message = it.message
                        )
                    )
                }
            )
        }
    }

    private fun postSocialInfo(socialInfo: Social) {
        val tempSocialInfo = Social(
            likeCount = socialInfo.likeCount.plus(randomNum()),
            commentCounts = CommentCounts(
                averageRating = socialInfo.commentCounts.averageRating,
                anonymousCommentsCount = socialInfo.commentCounts.anonymousCommentsCount.plus(randomNum()),
                memberCommentsCount = socialInfo.commentCounts.memberCommentsCount.plus(randomNum())
            )
        )
        _likeCount.value = tempSocialInfo.likeCount
        _socialInfo.value = tempSocialInfo
    }

    private fun startCoroutineTimer(
        delayMillis: Long = 0,
        repeatMillis: Long = 0,
        action: () -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    override fun onLikeClicked() {
        if (_likeState.value!!) {
            _likeCount.value = _likeCount.value?.minus(1)
            _likeState.value = false
        } else {
            _likeCount.value = _likeCount.value?.plus(1)
            _likeState.value = true
        }
    }

    private fun randomNum(): Int = Random.nextInt(1, 10)

    private companion object {
        const val TAG = "ProductDetailViewModel"
        const val REPEAT_TIME: Long = 20000
    }
}