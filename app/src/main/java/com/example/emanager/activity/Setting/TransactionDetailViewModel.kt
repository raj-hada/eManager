package com.example.emanager.activity.Setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emanager.roomDatebaseTransaction.Reposetery
import com.example.emanager.model.Transaction
import kotlinx.coroutines.launch

class TransactionDetailViewModel(private val reposetery: Reposetery) : ViewModel() {

    fun getTransactionDetail() : LiveData<List<Transaction>>{
        return reposetery.getAllTransactionList()
    }

    fun delete(transaction: Transaction){
        viewModelScope.launch {
            reposetery.deleteTransaction(transaction)
        }
    }

    fun deleteAllTransaction(){
        viewModelScope.launch {
            reposetery.deleteAllTransaction()
        }
    }


}