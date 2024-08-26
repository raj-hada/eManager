package com.example.emanager.roomDatebaseTransaction

import androidx.lifecycle.LiveData
import com.example.emanager.model.CategoryTotal
import com.example.emanager.model.Transaction
import java.util.Date

class Reposetery(private val dao: TransactionDao) {

    suspend fun insertTransaction(transaction: Transaction){
        dao.insert(transaction)
    }

    suspend fun deleteAllTransaction(){
        return dao.deleteAllTransaction()
    }

    fun getAllTransactionList() : LiveData<List<Transaction>>{
        return dao.getAllTransaction()
    }

    suspend fun deleteTransaction(transaction: Transaction){
        dao.delete(transaction)
    }
    fun getTotalAmount() : LiveData<Double>{
        return dao.getTotalAmount()
    }
    fun getTotalIncome() : LiveData<Double>{
        return dao.getTotalIncome()
    }
    fun getTotalExpense(): LiveData<Double>{
        return dao.getTotalExpense()
    }
    fun getDateTransaction(date : Date) : LiveData<List<Transaction>>{
        return dao.getDateTransaction(date)
    }
    fun getTodayIncome(date: Date) : LiveData<Double>{
        return dao.getTodayIncome(date)
    }
    fun getTodayExpense(date: Date) : LiveData<Double>{
        return dao.getTodayExpense(date)
    }
    fun getTodayAmount(date: Date) : LiveData<Double>{
        return dao.getTodayAmount(date)
    }

    fun getTransactionBetweenDate(pDate : Date,lDate : Date) : LiveData<List<Transaction>>{
        return dao.getTransactionBetweenDate(pDate,lDate)
    }

    fun getDayTransaction(date : Date) : LiveData<List<CategoryTotal>>{
        return dao.getDayTransaction(date)
    }

    fun getIncomeData(date: Date): LiveData<List<CategoryTotal>> {
        return dao.getIncomeDataByCategory(date)
    }

    fun getExpenseData(date: Date): LiveData<List<CategoryTotal>> {
        return dao.getExpenseDataByCategory(date)
    }


}