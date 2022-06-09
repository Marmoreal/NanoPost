package com.example.hw7.ui.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDateStringFrom(string: Long, patter: String): String{
    val simpleDateFormat = SimpleDateFormat(patter, Locale.getDefault())
    val dateString = simpleDateFormat.format(string).toString()
    return String.format(dateString)
}