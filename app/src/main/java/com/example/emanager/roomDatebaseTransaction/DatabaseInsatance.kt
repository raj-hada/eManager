package com.example.emanager.roomDatebaseTransaction

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.emanager.TypeConverter.DateTypeConverter
import com.example.emanager.model.Transaction

@Database(entities = [Transaction::class] , version = 1 , exportSchema = false )
@TypeConverters(DateTypeConverter::class)
abstract class DatabaseInsatance : RoomDatabase() {

    abstract fun getDao() : TransactionDao

    companion object{
        @Volatile
        private var Instance : DatabaseInsatance? = null

        fun getInstance(context: Context) : DatabaseInsatance {
            return Instance ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseInsatance::class.java,
                    "eManager"
                ).build()
                Instance = instance
                instance
            }
        }
    }
}