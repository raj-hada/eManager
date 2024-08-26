package com.example.emanager.activity.Setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emanager.roomDatebaseTransaction.Reposetery

class TransactionDetailViewModelFactory(private val reposetery: Reposetery) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionDetailViewModel(reposetery) as T
    }
}