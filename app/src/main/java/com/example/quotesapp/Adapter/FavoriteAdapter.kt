package com.example.quotesapp.Adapter

import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.quotesapp.Model.Model
import com.example.quotesapp.Model.favoratshayri
import com.example.quotesapp.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class FavoriteAdapter(
    var context: Context,
    var quoteslist: ArrayList<Model>,
    var like: (Int, Int) -> Unit
) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    var list = ArrayList<favoratshayri>()

    lateinit var manager : DownloadManager

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtshayari: TextView = view.findViewById(R.id.txtshayari)
        var imgCopy: ImageView = view.findViewById(R.id.imgCopy)
        var imgShare: ImageView = view.findViewById(R.id.imgShare)
        var imglike: ImageView = view.findViewById(R.id.imglike)
        var imgDownload: ImageView = view.findViewById(R.id.imgDownload)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(parent.context)
            .inflate(R.layout.display_category_data, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtshayari.text = list[position].Shayari_item

        holder.imglike.setImageResource(R.drawable.like)

        holder.imgCopy.setOnClickListener {
            val clipboad = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", holder.txtshayari.text)
            clipboad.setPrimaryClip(clip)
            Toast.makeText(context, "text copied", Toast.LENGTH_SHORT).show()
        }

        holder.imgShare.setOnClickListener {
            val p = Intent(Intent.ACTION_SEND)
            p.type = "text/plan"
            p.putExtra(Intent.EXTRA_TEXT, list[position].Shayari_item)
            context.startActivity(p)
        }

        holder.imglike.setOnClickListener {
            like.invoke(list[position].Shayari_id, 0)
            holder.imglike.setImageResource(R.drawable.love)
            list[position].fav = 0
            deleteItem(position)
        }

        holder.imgDownload.setOnClickListener {
           mSaveMediaToStorage(bitmap = null)
        }
    }

    private fun mSaveMediaToStorage(bitmap: Bitmap?) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context , "Saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }
    fun updateList(list: ArrayList<favoratshayri>) {
        this.list = list
        notifyDataSetChanged()
    }

    private fun deleteItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

}