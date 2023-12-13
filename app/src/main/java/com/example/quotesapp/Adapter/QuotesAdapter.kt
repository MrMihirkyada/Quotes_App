package com.example.quotesapp.Adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quotesapp.Model.DisplayCategoryModel
import com.example.quotesapp.Model.Model
import com.example.quotesapp.QuotesListActivity
import com.example.quotesapp.R

class QuotesAdapter(
    var context: Context,
    var quoteslist: ArrayList<Model>,
    var onitemSelect: (id: Int) -> Unit,
    var onShyariSelect: (Shyari: String) -> Unit,
    var like: (Int, Int) -> Unit
) : RecyclerView.Adapter<QuotesAdapter.MyviewHolder>() {

    class MyviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var lnrlayout: LinearLayout = view.findViewById(R.id.lnrlayout)
        var txtshayari: TextView = view.findViewById(R.id.txtshayari)
        var imgbtncopy: ImageButton = view.findViewById(R.id.imgbtncopy)
        var imgbtnshare: ImageButton = view.findViewById(R.id.imgbtnshare)
        var imgbtnlike: ImageView = view.findViewById(R.id.imgbtnlike)
        var imgbtnedit: ImageButton = view.findViewById(R.id.imgbtnedit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesAdapter.MyviewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.quotes_item, parent, false)
        var holder = MyviewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return quoteslist.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: QuotesAdapter.MyviewHolder, position: Int) {

        holder.txtshayari.text = quoteslist.get(position).shayari

        holder.txtshayari.setOnClickListener {
            onitemSelect.invoke(quoteslist.get(position).id)
        }

        holder.imgbtncopy.setOnClickListener {
            val clipboad = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", holder.txtshayari.text)
            clipboad.setPrimaryClip(clip)
            Toast.makeText(context, "text copied", Toast.LENGTH_SHORT).show()
        }

        holder.imgbtnshare.setOnClickListener {
            val p = Intent(Intent.ACTION_SEND)
            p.type = "text/plan"
            p.putExtra(Intent.EXTRA_TEXT, quoteslist[position].shayari)
            context.startActivity(p)
        }

        holder.imgbtnedit.setOnClickListener {
            onShyariSelect.invoke(quoteslist[position].shayari)
            var i = Intent(holder.imgbtnedit.context, QuotesListActivity::class.java)
            i.putExtra("id",quoteslist[position].id)
            i.putExtra("shayari", quoteslist[position].shayari)
            holder.imgbtnedit.context.startActivity(i)
        }



        holder.imgbtnlike.setOnClickListener{

            if (quoteslist[position].status == 0){


                holder.imgbtnlike.setImageResource(R.drawable.like)
                like.invoke(quoteslist[position].id,1)

//                Log.e("TAG", "onBindViewHolder: "+Quoteslist[position].shyari_id)
            }else{


                holder.imgbtnlike.setImageResource(R.drawable.love)
                like.invoke(quoteslist[position].id,0)
            }
        }

        holder.imgbtnlike.setOnClickListener{
            if (quoteslist[position].status == 1){
                like.invoke(quoteslist[position].id,0)
                holder.imgbtnlike.setImageResource(R.drawable.love)
                quoteslist[position].status = 0
                Log.e("TAG", "onBindViewHolder: "+quoteslist[position].status)
            }else{
                like.invoke(quoteslist[position].id,1)
                holder.imgbtnlike.setImageResource(R.drawable.like)
                quoteslist[position].status = 1
            }
        }
    }
//    fun updatelist(Quoteslist: ArrayList<Model>) {
//        this.quoteslist = ArrayList()
//        this.quoteslist.addAll(Quoteslist)
//        notifyDataSetChanged()
//    }
}