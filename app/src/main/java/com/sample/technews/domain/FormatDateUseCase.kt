package com.sample.technews.domain

import android.icu.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FormatDateUseCase @Inject constructor() {

    companion object {
        private const val DATE_FORMAT_ENCODE = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        private const val DATE_FORMAT_DECODE = "MMM dd, y hh:mm a"
    }

    operator fun invoke(date: String?): String? {
        val formatter = SimpleDateFormat(
            DATE_FORMAT_ENCODE,
            Locale.US
        ).parse(date)
        val formattedDate = formatter?.let {
            SimpleDateFormat(DATE_FORMAT_DECODE, Locale.US).format(it)
        }
        return formattedDate
    }

}