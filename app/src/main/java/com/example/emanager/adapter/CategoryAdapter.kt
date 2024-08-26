package com.example.emanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emanager.model.Category
import com.example.emanager.R
import com.example.emanager.databinding.CategorySelectBinding

class CategoryAdapter(private val context: Context,
                      private val arrayList: ArrayList<Category>,
                      private val addOnClickListener: (Category) -> Unit)
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding : CategorySelectBinding = CategorySelectBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_select, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = arrayList.size


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val cat = arrayList.get(position)
        holder.binding.categoryName.setText(cat.categoryName)
        holder.binding.categoryImg.setImageResource(cat.categoryImg)
        holder.binding.root.setOnClickListener {
            addOnClickListener(cat)
        }
    }
}