package com.example.demo1.base.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Any?.log(e: Any? = null) {
    if (e != null)
        println(this.toString() + e.toString())
    else
        println(this.toString())
}

object Utils {
    fun getNowFormatDate(pattern: String): String {
        val timeZone = TimeZone.getTimeZone("Etc/GMT-8")
        TimeZone.setDefault(timeZone)
        val nowDate = Date()
        val format = SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE)
        return format.format(nowDate)
    }

    fun getNowTimeInt(): Triple<Int, Int, Int> {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.now()
        val now = localDate.format(pattern).split('-')
        now.log()
        val year = now[0].toInt()
        val moth = now[1].toInt()
        val day = now[2].toInt()
        return Triple(year, moth, day)
    }

    fun getNowTimeString(): String {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTime = LocalDateTime.now()
        return localDateTime.format(pattern)
    }
}