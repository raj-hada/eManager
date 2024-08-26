package com.example.emanager.activity.Setting

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.TypeConverters
import com.example.emanager.roomDatebaseTransaction.Reposetery
import com.example.emanager.adapter.TransactionAdapter
import com.example.emanager.databinding.ActivityTransactionDetailBinding
import com.example.emanager.roomDatebaseTransaction.DatabaseInsatance
import com.example.emanager.TypeConverter.DateTypeConverter
import com.example.emanager.model.Transaction

class TransactionDetail : AppCompatActivity() {

    private lateinit var reposetery: Reposetery
    private lateinit var  transactionDetailViewModel: TransactionDetailViewModel
    private lateinit var arr : ArrayList<Transaction>
    private lateinit var adapter : TransactionAdapter


    private  val binding: ActivityTransactionDetailBinding by lazy{
        ActivityTransactionDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        reposetery = Reposetery(DatabaseInsatance.getInstance(this).getDao())
        transactionDetailViewModel = ViewModelProvider(this
            , TransactionDetailViewModelFactory(reposetery)
        )
            .get(TransactionDetailViewModel::class.java)

        binding.onBack.setOnClickListener{
            onBackPressed()
        }
        setRecyclerAdapter()

    }
    @TypeConverters(DateTypeConverter::class)
    private fun setRecyclerAdapter() {
        adapter = TransactionAdapter(this, emptyList(),{
            showDeleteConfirmationDialog(it)
        },{

        })
        binding.recycleTrascation.layoutManager = LinearLayoutManager(this)
        binding.recycleTrascation.adapter = adapter
        val divider = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        binding.recycleTrascation.addItemDecoration(divider)
        transactionDetailViewModel.getTransactionDetail().observe(this){ transaction ->
            adapter.updateTransactions(transaction)
        }
    }

    private fun showDeleteConfirmationDialog(it: Transaction) {
        AlertDialog.Builder(this)
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete this transaction")
            .setPositiveButton("Yes"){_,_ ->
                transactionDetailViewModel.delete(it)
            }
            .setNegativeButton("No",null)
            .show()
    }

}