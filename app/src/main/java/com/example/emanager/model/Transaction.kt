package com.example.emanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity(tableName = "transaction")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo(name = "type")
    var type : String,
    @ColumnInfo(name = "date")
    var date : Date,
    @ColumnInfo(name = "amount")
    var amount : Double,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "note")
    var note: String
)