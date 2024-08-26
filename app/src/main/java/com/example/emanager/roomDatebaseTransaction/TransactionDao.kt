package com.example.emanager.roomDatebaseTransaction

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.emanager.model.CategoryTotal
import com.example.emanager.model.Transaction
import java.util.Date

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Query("DELETE  FROM `transaction`")
    suspend fun deleteAllTransaction()

    @Query("SELECT * FROM `transaction`")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) AS TOTAL_AMOUNT FROM `transaction`")
    fun getTotalAmount(): LiveData<Double>

    @Query("SELECT SUM(amount) AS TOTAL_INCOME FROM `transaction` WHERE type ='Income' ")
    fun getTotalIncome(): LiveData<Double>

    @Query("SELECT SUM(amount) AS TOTAL_EXPENSE FROM `transaction` WHERE type ='Expense' ")
    fun getTotalExpense(): LiveData<Double>

    @Query("SELECT * FROM `transaction` WHERE date = :date")
    fun getDateTransaction(date: Date): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) AS TOTAL_AMOUNT FROM `transaction` WHERE date = :date")
    fun getTodayAmount(date: Date): LiveData<Double>

    @Query("SELECT SUM(amount) AS TODAY_INCOME FROM `transaction` WHERE type = 'Income' AND date = :date")
    fun getTodayIncome(date: Date): LiveData<Double>

    @Query("SELECT SUM(amount) AS TODAY_EXPENSE FROM `transaction` WHERE type = 'Expense' AND date = :date")
    fun getTodayExpense(date: Date): LiveData<Double>

    @Query("SELECT * FROM `transaction` WHERE date BETWEEN :sdate AND :ldate")
    fun getTransactionBetweenDate(sdate: Date, ldate: Date): LiveData<List<Transaction>>

    @Query("SELECT category, SUM(amount) AS total FROM `transaction` WHERE type = 'Income' AND date = :date GROUP BY category")
    fun getIncomeDataByCategory(date: Date) : LiveData<List<CategoryTotal>>

    @Query("SELECT category, SUM(amount) AS total FROM `transaction` WHERE type = 'Expense' AND date = :date GROUP BY category")
    fun getExpenseDataByCategory(date: Date) : LiveData<List<CategoryTotal>>

    @Query("SELECT  category, SUM(amount) AS total FROM `transaction` WHERE date = :date GROUP BY category")
    fun getDayTransaction(date: Date): LiveData<List<CategoryTotal>>

}
