package com.example.emanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emanager.R
import com.example.emanager.databinding.TransactionCardBinding
import com.example.emanager.model.Transaction
import com.example.emanager.utils.Constants
import com.example.emanager.utils.Helper

class TransactionAdapter(private val context: Context,
                         private var transactions: List<Transaction>,
                         private val listener : (Transaction) -> Unit,
                         private var onLongClick : (Transaction) -> Unit,
)
    : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {


    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : TransactionCardBinding = TransactionCardBinding.bind(itemView)
        init {
            itemView.setOnLongClickListener {
                onLongClick(transactions[adapterPosition]) // Notify on long click
                true
            }
            itemView.setOnClickListener{
                listener(transactions[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_card,parent,false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions.get(position)
        var typeImg : Int
        var categoryImg : Int

        if(transaction.type == Constants.INCOME){
           typeImg = R.drawable.credit
            categoryImg = when(transaction.category){
                Constants.BONUS -> R.drawable.bonus
                Constants.SALARY -> R.drawable.salary
                Constants.INSURANCECLAIM -> R.drawable.insurance
                Constants.RENTALINCOME -> R.drawable.rent
                Constants.PROFIT -> R.drawable.profit
                else-> R.drawable.others
            }
        }else{
            typeImg = R.drawable.debit
            categoryImg = when(transaction.category){
                Constants.GROCERIES -> R.drawable.groceries
                Constants.TRANSPORTATION -> R.drawable.transportations
                Constants.INSURANCE -> R.drawable.insurance
                Constants.UTILITIES -> R.drawable.utilities
                Constants.SAVING -> R.drawable.saving
                else -> R.drawable.others
            }
        }
        val amount = Math.abs(transaction.amount)
        val date = Helper.formatDate(transaction.date)
        holder.binding.transactionImg.setImageResource(typeImg)
        holder.binding.detail.text = transaction.note
        holder.binding.amount.text = amount.toString()
        holder.binding.date.text = date
        holder.binding.categoryImage.setImageResource(categoryImg)
    }
    fun updateTransactions(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
}