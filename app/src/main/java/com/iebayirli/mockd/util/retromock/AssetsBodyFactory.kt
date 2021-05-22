package com.iebayirli.mockd.util.retromock

import android.content.res.AssetManager
import co.infinum.retromock.BodyFactory
import java.io.InputStream
import javax.inject.Inject

class AssetsBodyFactory @Inject constructor(
    private val assetManager: AssetManager
) : BodyFactory {

    override fun create(input: String): InputStream {
        return assetManager.open(input)
    }

    private companion object {
        const val TAG = "AssetsBodyFactory"
    }

}