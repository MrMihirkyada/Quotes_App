package com.example.quotesapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.quotesapp.Database.DatabaseHelper
import com.example.quotesapp.databinding.ActivityQuotesListBinding
import java.io.IOException

class QuotesListActivity : AppCompatActivity() {

    lateinit var binding: ActivityQuotesListBinding
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        toolbar()
    }

    private fun toolbar() {
        val toolbar = findViewById<Toolbar>(R.id.colortoolbar)
        setSupportActionBar(toolbar)
        initview()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.color_change_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.white) {
            binding.editquotes.setTextColor(Color.WHITE)
            Toast.makeText(this, "White Color is Selected", Toast.LENGTH_SHORT).show()
            return true
        } else if (id == R.id.Black) {
            binding.editquotes.setTextColor(Color.BLACK)
            Toast.makeText(this, "Black Color is Selected", Toast.LENGTH_SHORT).show()
            return true
        }
        return if (id == R.id.DarkGrey) {
            binding.editquotes.setTextColor(Color.DKGRAY)
            Toast.makeText(this, "DarkGrey Color is Selected", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.Red) {
            binding.editquotes.setTextColor(Color.RED)
            Toast.makeText(this, "Red Color is Selected", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.Green) {
            binding.editquotes.setTextColor(Color.GREEN)
            Toast.makeText(this, "Green Color is Selected", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.Yellow) {
            binding.editquotes.setTextColor(Color.YELLOW)
            Toast.makeText(this, "Yellow Color is Selected", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.Blue) {
            binding.editquotes.setTextColor(Color.BLUE)
            Toast.makeText(this, "Blue Color is Selected", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.Grey) {
            binding.editquotes.setTextColor(Color.GRAY)
            Toast.makeText(this, "Grey Color is Selected", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.Magenta) {
            binding.editquotes.setTextColor(Color.MAGENTA)
            Toast.makeText(this, "Magenta Color is Selected", Toast.LENGTH_SHORT).show()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun initview() {

        var db = DatabaseHelper(this)

//        colorPickerView = findViewById(R.id.colorPickerView);


        if (intent != null) {
            id = intent.getIntExtra("id", 0)
            var shayari = intent.getStringExtra("shayari")

            binding.editquotes.setText(shayari)
        }
        binding.imgAdd.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(i, "Select Picture"), 100)
        }

        binding.imgDownload.setOnClickListener {
            val z: View = binding.Screenshot
            z.isDrawingCacheEnabled = true
            val totalHeight: Int = z.getHeight()
            val totalWidth: Int = z.getWidth()
            z.layout(0, 0, totalWidth, totalHeight)
            z.buildDrawingCache(true)
            val bm: Bitmap = Bitmap.createBitmap(z.drawingCache)
            z.isDrawingCacheEnabled = false
            Toast.makeText(this@QuotesListActivity, "Download Successfully", Toast.LENGTH_SHORT)
                .show()
            MediaStore.Images.Media.insertImage(contentResolver, bm, null, null)
        }


        binding.imgbtnSave.setOnClickListener {
            var Quotes = binding.editquotes.text.toString()

            db.updatequotesRecord(id, Quotes)
            Log.e("TAG", "updatequotesRecord: " + id)

            Toast.makeText(this, "$id" + "$Quotes", Toast.LENGTH_SHORT).show()

            Toast.makeText(this, "record update successfully", Toast.LENGTH_SHORT).show()

            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null && data.data != null) {
                val selectedImageUri = data.data
                var selectedImageBitmap: Bitmap? = null
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        selectedImageUri
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                binding.imgShow.setImageBitmap(selectedImageBitmap)
            }
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            val photo = data!!.extras!!["data"] as Bitmap?
            // Set the image in imageview for display
        }
    }

}