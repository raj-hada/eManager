package com.example.emanager.Analytics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.TypeConverters
import com.example.emanager.model.CategoryTotal
import com.example.emanager.utils.Helper
import com.example.emanager.R
import com.example.emanager.roomDatebaseTransaction.Reposetery
import com.example.emanager.databinding.FragmentAnalyticsBinding
import com.example.emanager.roomDatebaseTransaction.DatabaseInsatance
import com.example.emanager.TypeConverter.DateTypeConverter
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.Calendar
import java.util.Date


class AnalyticsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: FragmentAnalyticsBinding
    private lateinit var viewModel: AnalyticsViewModel
    private lateinit var pieChart: PieChart
    var chartText : String = ""

    val cal  = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAnalyticsBinding.inflate(layoutInflater)
        val repo = Reposetery(DatabaseInsatance.getInstance(requireContext()).getDao())
        viewModel = ViewModelProvider(this,AnalyticsViewModelFactory(repo))[AnalyticsViewModel::class.java]


        pieChart = binding.chart

        updateDate()

        binding.nextDay.setOnClickListener{
            cal.add(Calendar.DATE,1)
            updateDate()
        }
        binding.previousDay.setOnClickListener {
            cal.add(Calendar.DATE,-1)
            updateDate()
        }

        setupChart()

// Set click listeners to switch between income and expense data
        binding.incomeBtn.setOnClickListener {
            binding.expenseBtn.setBackgroundResource(R.drawable.default_shape)
            binding.incomeBtn.setBackgroundResource(R.drawable.income_selector)
            pieChart.centerText = "Income Data"
            viewModel.loadIncomeData(Helper.parseDate(binding.date.text.toString()))
        }

        binding.expenseBtn.setOnClickListener {
            binding.expenseBtn.setBackgroundResource(R.drawable.expense_selector)
            binding.incomeBtn.setBackgroundResource(R.drawable.default_shape)
            pieChart.centerText = "Expense Data"
            viewModel.loadExpenseData(Helper.parseDate(binding.date.text.toString()))
        }

        // Observe chart data changes
        viewModel.chartData.observe(viewLifecycleOwner, Observer { data ->
            Log.d("AnalyticsFragment", "Received chart data: $data")
            if(data.isEmpty()){
                binding.emptyTransaction.visibility = View.VISIBLE
                binding.chart.visibility = View.GONE
            }else{
                binding.chart.visibility = View.VISIBLE
                binding.emptyTransaction.visibility = View.GONE
                updateChart(data)
            }
            Log.d("RAJ HADA", data.toString())
        })
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateChart(data: List<CategoryTotal>) {
        val entries = data.map { entry ->
            PieEntry(entry.total.toFloat(), entry.category as String)
        }
        val dataSet = PieDataSet(entries, "Transactions")
        dataSet.colors = listOf(Color.RED, Color.GREEN,  Color.BLUE, Color.YELLOW)
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate()// Refresh the chart with new data
    }

    private fun setupChart() {
        pieChart.apply {
            description.isEnabled = false
            isRotationEnabled = true
            setEntryLabelColor(Color.BLACK)
            setCenterTextSize(20f)
        }
    }

    @TypeConverters(DateTypeConverter::class)
    private fun updateDate() {
        val date = Helper.formatDate(cal.time)
        pieChart.centerText = chartText
        binding.date.text = date
        observeTransactionDate(Helper.parseDate(date))
    }

    private fun observeTransactionDate(date: Date) {
        viewModel.dateTransaction(date).observe(viewLifecycleOwner){
            viewModel.loadData(date)
            pieChart.centerText = "Trasaction Data"
        }
        

    }
}
