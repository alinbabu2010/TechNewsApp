package com.sample.technews.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.sample.technews.domain.model.ArticleInfo

class ArticleInfoType : NavType<ArticleInfo>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): ArticleInfo? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): ArticleInfo {
        return Gson().fromJson(value, ArticleInfo::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ArticleInfo) {
        bundle.putParcelable(key, value)
    }

}