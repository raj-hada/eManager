package com.example.emanager.activity.mainActivity

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.TypeConverters
import com.example.emanager.R
import com.example.emanager.roomDatebaseTransaction.Reposetery
import com.example.emanager.adapter.TransactionAdapter
import com.example.emanager.activity.Setting.SettingActivity
import com.example.emanager.databinding.FragmentHomeActivityBinding
import com.example.emanager.roomDatebaseTransaction.DatabaseInsatance
import com.example.emanager.TypeConverter.DateTypeConverter
import com.example.emanager.utils.SP
import com.example.emanager.model.Transaction
import com.example.emanager.utils.Helper
import java.util.Calendar
import java.util.Date
import kotlin.math.abs


class HomeActivityFragment : Fragment() {

    private lateinit var binding : FragmentHomeActivityBinding
    val cal  = Calendar.getInstance()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var arr : ArrayList<Transaction>
    private lateinit var adapter : TransactionAdapter
    private lateinit var sp: SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeActivityBinding.inflate(layoutInflater)
        sp = SP.getSharedPreferenceInstance(requireContext())
        val reposetery = Reposetery(DatabaseInsatance.getInstance(requireContext()).getDao())
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(reposetery)).get(MainViewModel::class.java)


        val userName = sp.getString("NAME","")
        if(userName == ""){
            binding.name.text = "Welcome"
        }else{
            binding.name.text = userName
        }

        // set Greeting in Titlebar
        var hour = cal.get(Calendar.HOUR_OF_DAY)
        if(hour in 0..11) binding.greeting.text = "Good Morning"
        else if(hour in 18..24) binding.greeting.text = "Good Night"
        else binding.greeting.text = "Good Day"

        binding.setting.setOnClickListener{
            startActivity(Intent(requireContext(), SettingActivity::class.java))
        }

        // set date
        updateDate()

        binding.nextDay.setOnClickListener{
            cal.add(Calendar.DATE,1)
            updateDate()
        }
        binding.previousDay.setOnClickListener {
            cal.add(Calendar.DATE,-1)
            updateDate()
        }



        // set bottom-navigation-fragment
        binding.addTransaction.setOnClickListener{
            AddTransactionFragment().show(parentFragmentManager,null)
        }

        // set recycler adapter
        setRecyclerAdapter()

        mainViewModel.totalAmount.observe(viewLifecycleOwner){
            if(it == null){
                binding.totalCost.text = "0.0"
            }else {
                if(it >= 0.0){
                    binding.totalCost.setTextColor(resources.getColor(R.color.green))
                    binding.totalCost.text = it.toString()
                }else{
                    binding.totalCost.setTextColor(resources.getColor(R.color.red))
                    binding.totalCost.text = abs(it).toString()

                }

            }
        }

        mainViewModel.totalIncome.observe(viewLifecycleOwner){
            if(it == null){
                binding.incomeCost.text = "0.0"
                binding.incomeCost.setTextColor(resources.getColor(R.color.black))
            }else {
                binding.incomeCost.text = abs(it).toString()
            }
        }
        mainViewModel.totalExpense.observe(viewLifecycleOwner){
            if(it == null){
                binding.expenseCost.text = "0.0"
            }else {
                binding.expenseCost.text = abs(it).toString()
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    @TypeConverters(DateTypeConverter::class)
    private fun setRecyclerAdapter() {
        adapter = TransactionAdapter(
            context = requireContext(),
            transactions = emptyList(),
            { showTransactionDetailDialog(it) },
            { showDeleteConfirmationDialog(it) }
        )

        binding.recycleTrascation.layoutManager = LinearLayoutManager(context)
        binding.recycleTrascation.adapter = adapter

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recycleTrascation.addItemDecoration(divider)

        mainViewModel.dateTransaction(date = Helper.parseDate(binding.date.text.toString()))
            .observe(viewLifecycleOwner) { transactions ->
                adapter.updateTransactions(transactions)


            }
    }


    private fun showTransactionDetailDialog(it: Transaction) {
    }

    private fun showDeleteConfirmationDialog(it: Transaction) {
        AlertDialog.Builder(context)
            .setIcon(R.drawable.remove)
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete this transaction")
            .setPositiveButton("Yes"){_,_ ->
                mainViewModel.delete(it)
            }
            .setNegativeButton("No",null)
            .show()
    }


    @TypeConverters(DateTypeConverter::class)
    private fun updateDate() {
        val date = Helper.formatDate(cal.time)
        binding.date.text = date
        observeTransactionDate(Helper.parseDate(date))
    }

    private fun observeTransactionDate(date: Date) {
        mainViewModel.dateTransaction(date).observe(viewLifecycleOwner){
            if (it.isEmpty()) {
                // If transactions are empty, show empty state image only if the date remains the same
                binding.recycleTrascation.visibility = View.GONE
                binding.emptyTransaction.visibility = View.VISIBLE
            } else {
                // Show the RecyclerView and hide the empty state image if transactions are available
                binding.recycleTrascation.visibility = View.VISIBLE
                binding.emptyTransaction.visibility = View.GONE
            }
            adapter.updateTransactions(it)
            updateAmount(date)
        }
    }

    private fun updateAmount(time: Date){
        mainViewModel.todayAmount(time).observe(viewLifecycleOwner){
            if(it == null){
                binding.dayCost.text = "0.0"
            }else {
                if(it >= 0.0){
                    binding.dayCost.text = it.toString()
                }else{
                    binding.dayCost.text = abs(it).toString()

                }
            }
        }
        mainViewModel.todayIncome(time).observe(viewLifecycleOwner){
            Log.d("RAJHADA",it.toString())
            if(it == null){
                binding.dayIncome.text = "0.0"
            }else {
                binding.dayIncome.text = it.toString()
                binding.dayIncome.setTextColor(resources.getColor(R.color.green))
            }
        }
        mainViewModel.todayExpense(time).observe(viewLifecycleOwner){
            if(it == null){
                binding.dayExepense.text = "0.0"
            }else {
                binding.dayExepense.setTextColor(resources.getColor(R.color.red))
                binding.dayExepense.text = abs(it).toString()
            }
        }
    }

}