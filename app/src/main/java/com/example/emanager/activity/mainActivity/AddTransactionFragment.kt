package com.example.emanager.activity.mainActivity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.emanager.adapter.CategoryAdapter
import com.example.emanager.R
import com.example.emanager.roomDatebaseTransaction.Reposetery
import com.example.emanager.model.Transaction
import com.example.emanager.databinding.CategoryDialogBinding
import com.example.emanager.databinding.FragmentAddTransactionBinding
import com.example.emanager.roomDatebaseTransaction.DatabaseInsatance
import com.example.emanager.utils.Constants
import com.example.emanager.utils.Helper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.Date


class AddTransactionFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTransactionBinding
    private val cal = Calendar.getInstance()
    private lateinit var repo : Reposetery
    private lateinit var mainViewModel : MainViewModel
    private var isCredit = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = Reposetery(
            DatabaseInsatance.getInstance(requireContext()).getDao()
        )
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repo)).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentAddTransactionBinding.inflate(inflater)
        var transaction : Transaction
        var cal = Calendar.getInstance()
        val currentDate = Helper.formatDate(Date())
        binding.date.setText(currentDate)
        var date = currentDate


        binding.date.setOnClickListener{
            showDatePickerDialog()
        }
        binding.category.setOnClickListener {showDebitCategory()}

        binding.credit.setOnClickListener {
            isCredit =true
            binding.credit.setBackgroundResource(R.drawable.income_selector)
            binding.debit.setBackgroundResource(R.drawable.default_shape)
            binding.category.text = null
            binding.category.setOnClickListener {
                showCreditCategory()
            }
        }
        binding.debit.setOnClickListener {
            isCredit = false
            binding.debit.setBackgroundResource(R.drawable.expense_selector)
            binding.credit.setBackgroundResource(R.drawable.default_shape)
            binding.category.text = null
            binding.category.setOnClickListener {
                showDebitCategory()
            }
        }

        binding.reset.setOnClickListener {
            resetTransactionDetail()
        }

        binding.save.setOnClickListener {
            saveTransaction()
        }



        // Inflate the layout for this fragment
        return binding.root
    }

    private fun saveTransaction() {
        val type = if(isCredit){
            Constants.INCOME
        }else{
            Constants.EXPENSE
        }

        val date = Helper.parseDate(binding.date.text.toString())

        val amount = binding.amount.text.toString().toDoubleOrNull() ?: 0.0
        val category = binding.category.text.toString()
        val note = binding.note.text.toString()
        if(TextUtils.isEmpty(amount.toString())){
            binding.amount.error = "Enter amount"
            binding.amount.requestFocus()
            return
        }
        if(TextUtils.isEmpty(category)){
            binding.category.error = "Please Select Category"
            binding.category.requestFocus()
            return
        }
        if(TextUtils.isEmpty(note)){
            binding.note.error = "Please Select Category"
            binding.note.requestFocus()
            return
        }

        if(amount == 0.0){
            binding.amount.error = "Enter valid amount"
            binding.amount.requestFocus()
            return
        }
        val transaction = Transaction(
            type = type,
            date = date,
            amount = if(type == Constants.INCOME) amount else -amount,
            category = category,
            note = note,
        )
        mainViewModel.insert(transaction)
        dismiss()
    }

    private fun resetTransactionDetail() {
        binding.date.setText(Helper.formatDate(Date()))
        binding.amount.text = null
        binding.category.text = null
        binding.note.text = null
        binding.image.text =null
    }

    private fun showDebitCategory(){
        Constants.setExpenseCategory()
        val bind = CategoryDialogBinding.inflate(LayoutInflater.from(context))
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(bind.getRoot())
        val categoryAdapter = CategoryAdapter(
            requireContext(),
            Constants.Expense_Array
        ) {
            binding.category.setText(it.categoryName)
            alertDialog.dismiss()
        }
        bind.recyclerCategory.layoutManager = GridLayoutManager(requireContext(),3)
        bind.recyclerCategory.adapter = categoryAdapter
        alertDialog.show()
    }

    private fun showCreditCategory() {
        Constants.setIncomeCategory()
        val bind = CategoryDialogBinding.inflate(LayoutInflater.from(context))
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(bind.getRoot())
        val categoryAdapter = CategoryAdapter(
            requireContext(),
            Constants.Income_Array
        ) {
            binding.category.setText(it.categoryName)
            alertDialog.dismiss()
        }
        bind.recyclerCategory.layoutManager = GridLayoutManager(requireContext(),3)
        bind.recyclerCategory.adapter = categoryAdapter
        alertDialog.show()
    }

    private fun showDatePickerDialog() {
    val datePicker = DatePickerDialog(
        requireContext(),
        { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val selectedDate = cal.time
            val formattedDate = Helper.formatDate(selectedDate)
            binding.date.setText(formattedDate)
        },
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DAY_OF_MONTH)
    )
    datePicker.show()
}
}