package com.example.emanager.Analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emanager.model.CategoryTotal
import com.example.emanager.roomDatebaseTransaction.Reposetery
import java.util.Date
import kotlin.math.abs

class AnalyticsViewModel(private val reposetery: Reposetery) : ViewModel(){

    fun dateTransaction(date : Date) : LiveData<List<CategoryTotal>> {
        return reposetery.getDayTransaction(date)
    }

    fun getIncomeData(date: Date): LiveData<List<CategoryTotal>> {
        return reposetery.getIncomeData(date)
    }

    fun getExpenseData(date: Date): LiveData<List<CategoryTotal>> {
        return reposetery.getExpenseData(date)
    }

    private val _chartData = MutableLiveData<List<CategoryTotal>>()
    val chartData: LiveData<List<CategoryTotal>> get() = _chartData


    fun loadIncomeData(date: Date) {
        getIncomeData(date).observeForever { data ->
            var chartData = data.map { CategoryTotal(it.category, it.total) }
            _chartData.value = chartData
        }
    }

    fun loadExpenseData(date: Date) {
        getExpenseData(date).observeForever { data ->
            var chartData = data.map { CategoryTotal(it.category, abs( it.total)) }
            _chartData.value = chartData
        }
    }

    fun loadData(date : Date){
        dateTransaction(date).observeForever{data ->
            var chartData = data.map{ CategoryTotal(it.category, abs(it.total)) }
            _chartData.value = chartData
        }
    }
}