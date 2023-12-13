package com.example.quotesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.Adapter.QuotesAdapter
import com.example.quotesapp.Database.DatabaseHelper
import com.example.quotesapp.Model.Model
import com.example.quotesapp.databinding.ActivityQuotesBinding

class QuotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuotesBinding
    lateinit var db: DatabaseHelper
    lateinit var quoteslist: ArrayList<Model>
    lateinit var myAdapter: QuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }

    private fun initview() {
        db = DatabaseHelper(this)

        var id = intent.getIntExtra("id", -1)

        if (id == 1) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Happy"
        } else if (id == 2) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Sad"
        } else if (id == 3) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Emotional"
        } else if (id == 4) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Life"
        } else if (id == 5) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Inspirational"
        } else if (id == 6) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Motivational"
        } else if (id == 7) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Love"
        } else if (id == 8) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Funny"
        } else if (id == 9) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Wisdom"
        } else if (id == 10) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Success"
        } else if (id == 11) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Friendship"
        } else if (id == 12) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Leadership"
        } else if (id == 13) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Religious/Spiritual"
        } else if (id == 14) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Belives"
        } else if (id == 15) {
            db.DisplayQuotesData(id)
            binding.txtquotes.text = "Money"
        }

        binding.imgback.setOnClickListener {
            onBackPressed()
        }

        quoteslist = db.DisplayQuotesData(id)




//        db.updateRecord(id,like)

        myAdapter = QuotesAdapter(this, quoteslist, onitemSelect = {

        }, onShyariSelect = {}, like = {id,status->
            db.updateRecord(id,status)
        })

        val layoutManager = LinearLayoutManager(this@QuotesActivity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.layoutManager = layoutManager

        binding.recyclerview.adapter = myAdapter
    }

//    override fun onResume()
//    {
//        super.onResume()
//
//        var quoteslist = db.DisplayQuotesData(id)
//
//        myAdapter.updatelist(quoteslist)
//    }
}