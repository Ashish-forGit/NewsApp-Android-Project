package com.aas.newspract

import android.text.format.DateUtils
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {
        fun getTimeAgo(temp: String): CharSequence {

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            var ago: CharSequence = ""
            try {
                val time: Long = sdf.parse(temp).time
                val now = System.currentTimeMillis()
                ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
            } catch (e: ParseException) {
                e.printStackTrace()
                Log.e("Utils","error $e")
            }

            return ago
        }

    }
}