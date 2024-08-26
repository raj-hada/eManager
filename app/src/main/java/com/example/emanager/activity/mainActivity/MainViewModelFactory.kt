package com.example.emanager.activity.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emanager.roomDatebaseTransaction.Reposetery

class MainViewModelFactory(private val reposetery: Reposetery) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(reposetery) as T
    }
}