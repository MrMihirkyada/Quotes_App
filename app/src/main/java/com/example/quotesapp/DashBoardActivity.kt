package com.example.quotesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.Adapter.CategoryAdapter
import com.example.quotesapp.Database.DatabaseHelper
import com.example.quotesapp.Model.Model
import com.example.quotesapp.Model.categoryModel
import com.example.quotesapp.databinding.ActivityDashBoardBinding

class DashBoardActivity : AppCompatActivity() {
    lateinit var list: ArrayList<Model>
    lateinit var myAdapter: CategoryAdapter
    lateinit var db: DatabaseHelper

    var categorylist = ArrayList<categoryModel>()

    lateinit var binding: ActivityDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }

    private fun initview() {

        db = DatabaseHelper(this)

        categorylist = db.DisplaycategoryData()

        binding.imglike.setOnClickListener {
            var i = Intent(this,FavoriteActivity::class.java)
            startActivity(i)
        }

        myAdapter = CategoryAdapter(categorylist, onitemclick = { id ->

            var i = Intent(this@DashBoardActivity,QuotesActivity::class.java)
            i.putExtra("id",id)
            startActivity(i)
        })

        val layoutManager = LinearLayoutManager(this@DashBoardActivity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.layoutManager = layoutManager

        binding.recyclerview.adapter = myAdapter
    }
}