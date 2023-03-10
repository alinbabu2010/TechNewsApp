package com.sample.technews.ui.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.sample.technews.domain.model.ArticleInfo

class ArticleInfoType : NavType<ArticleInfo>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): ArticleInfo? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, ArticleInfo::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): ArticleInfo {
        return Gson().fromJson(value, ArticleInfo::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ArticleInfo) {
        bundle.putParcelable(key, value)
    }

}