package com.example.emanager.utils

import com.example.emanager.model.Category
import com.example.emanager.R


class Constants {

    companion object{

        val EXPENSE = "Expense"
        val INCOME ="Income"

        val SALARY = "Salary"
        val BONUS = "Bonus"
        val INSURANCECLAIM = "Insurance Claim"
        val RENTALINCOME = "Rental Income"
        val PROFIT= "Profit"
        val OTHER = "Other"
        val  GROCERIES = "Groceries"
        val TRANSPORTATION = "Transportation"
        val UTILITIES = "Utilities"
        val SAVING ="Saving"
        val INSURANCE = "Insurance"
        val MISCELLANEOUS = "Miscellaneous"

        lateinit var Income_Array : ArrayList<Category>
        lateinit var Expense_Array : ArrayList<Category>

        fun setIncomeCategory() {
            Income_Array = ArrayList()
            Income_Array.add(Category("Salary", categoryImg = R.drawable.salary))
            Income_Array.add(Category("Bonus", R.drawable.bonus))
            Income_Array.add(Category("Insurance Claim", R.drawable.insurance))
            Income_Array.add(Category("Rental Income", R.drawable.rent))
            Income_Array.add(Category("Profit", R.drawable.profit))  // Ensure this is the correct resource name
            Income_Array.add(Category("Other", R.drawable.others))
        }
        fun setExpenseCategory() {
            Expense_Array = ArrayList()
            Expense_Array.add(Category("Groceries", R.drawable.groceries))
            Expense_Array.add(Category("Transportation", R.drawable.transportations))
            Expense_Array.add(Category("Insurance", R.drawable.insurance))
            Expense_Array.add(Category("Utilities", R.drawable.utilities))
            Expense_Array.add(Category("Saving", R.drawable.saving))
            Expense_Array.add(Category("Miscellaneous", R.drawable.others))
        }
    }
}