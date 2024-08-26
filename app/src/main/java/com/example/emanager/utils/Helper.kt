package com.example.emanager.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Helper {

    companion object{
        private val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        fun formatDate(date: Date): String{

            return dateFormat.format(date)
        }
        fun parseDate (dateString: String) : Date {
            return dateFormat.parse(dateString) ?: Date()
        }
    }
}