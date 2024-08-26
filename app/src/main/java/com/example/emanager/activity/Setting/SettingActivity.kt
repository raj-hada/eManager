package com.example.emanager.activity.Setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.emanager.roomDatebaseTransaction.Reposetery
import com.example.emanager.databinding.ActivitySettingBinding
import com.example.emanager.roomDatebaseTransaction.DatabaseInsatance
import com.example.emanager.utils.SP
import com.example.emanager.model.Transaction

class SettingActivity : AppCompatActivity() {
    private lateinit var reposetery: Reposetery
    private lateinit var  transactionDetailViewModel: TransactionDetailViewModel
    private lateinit var arr : ArrayList<Transaction>
    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reposetery = Reposetery(DatabaseInsatance.getInstance(this).getDao())
        transactionDetailViewModel = ViewModelProvider(this
            , TransactionDetailViewModelFactory(reposetery)
        )
            .get(TransactionDetailViewModel::class.java)

        binding.profile.setOnClickListener{
            startActivity(Intent(this@SettingActivity, ProfileActivity::class.java))
        }


        binding.getAllEntries.setOnClickListener{
            startActivity(Intent(this@SettingActivity, TransactionDetail::class.java))
        }

        binding.deleteAll.setOnClickListener{
            showDeleteConfirmationDialog()
        }

        binding.RemoveAllData.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("Remove Data")
                .setMessage("Are you sure want to remove your data")
                .setPositiveButton("Yes"){_,_->
                    SP.clearData(this)
                    transactionDetailViewModel.deleteAllTransaction()
                }
                .setNegativeButton("No",null)
                .show()
        }

    }
    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete All transaction")
            .setPositiveButton("Yes"){_,_ ->
                transactionDetailViewModel.deleteAllTransaction()
            }
            .setNegativeButton("No",null)
            .show()
    }
}