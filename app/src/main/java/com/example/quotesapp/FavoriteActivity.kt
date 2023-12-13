package com.example.quotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.Adapter.FavoriteAdapter
import com.example.quotesapp.Database.DatabaseHelper
import com.example.quotesapp.Model.Model
import com.example.quotesapp.Model.favoratshayri
import com.example.quotesapp.databinding.ActivityFavoriteBinding


class FavoriteActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper
    var list = ArrayList<favoratshayri>()
    var quoteslist = ArrayList<Model>()
    lateinit var adapter: FavoriteAdapter

    lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseHelper(this)
        initview()
    }

    private fun initview() {

        list = db.FavoriteDisplayRecord()

        adapter = FavoriteAdapter (this,quoteslist) { Shayari_id,fav -> db.updateRecord(Shayari_id, fav) }


        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvfavorite.layoutManager = manager
        binding.rcvfavorite.adapter = adapter
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

//        list = db.updateRecord()
//        adapter.updateList(list)
        adapter.updateList(list)
    }
}