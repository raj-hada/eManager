package com.example.emanager.Analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emanager.roomDatebaseTransaction.Reposetery

class AnalyticsViewModelFactory(private val reposetery: Reposetery) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnalyticsViewModel(reposetery) as T
    }
}