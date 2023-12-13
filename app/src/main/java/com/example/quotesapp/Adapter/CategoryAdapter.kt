package com.example.quotesapp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotesapp.Model.categoryModel
import com.example.quotesapp.R

class CategoryAdapter(var categoryModel : ArrayList<categoryModel>, var onitemclick : ((id:Int)->Unit)) : RecyclerView.Adapter<CategoryAdapter.MyviewHolder>() {

    class MyviewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var txtshayari : TextView = view.findViewById(R.id.txtcategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.MyviewHolder
    {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        var holder = MyviewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: CategoryAdapter.MyviewHolder, position: Int) {
        holder.txtshayari.text = categoryModel.get(position).category


        holder.txtshayari.setOnClickListener {
            onitemclick.invoke(categoryModel.get(position).id)
        }

        Log.e("TAG", "onBindViewHolder: "+holder.txtshayari)
    }

    override fun getItemCount(): Int {
        return categoryModel.size
    }
}