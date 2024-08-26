package com.example.emanager.activity.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emanager.roomDatebaseTransaction.Reposetery
import com.example.emanager.model.Transaction
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel(private val reposetery: Reposetery) : ViewModel() {
    val getTransaction : LiveData<List<Transaction>> = reposetery.getAllTransactionList()
    fun dateTransaction(date : Date) : LiveData<List<Transaction>> {
        return reposetery.getDateTransaction(date)
    }

    fun insert(transaction: Transaction){
        viewModelScope.launch {
            reposetery.insertTransaction(transaction)
        }
    }
    fun delete(transaction: Transaction){
        viewModelScope.launch {
            reposetery.deleteTransaction(transaction)
        }
    }
    val totalAmount : LiveData<Double> = reposetery.getTotalAmount()
    val totalIncome : LiveData<Double> = reposetery.getTotalIncome()
    val totalExpense : LiveData<Double> = reposetery.getTotalExpense()

    fun todayAmount(date : Date) : LiveData<Double>{
        return reposetery.getTodayAmount(date);
    }
    fun todayIncome(date : Date) : LiveData<Double>{
        return reposetery.getTodayIncome(date);
    }
    fun todayExpense(date : Date) : LiveData<Double>{
        return reposetery.getTodayExpense(date);
    }


}