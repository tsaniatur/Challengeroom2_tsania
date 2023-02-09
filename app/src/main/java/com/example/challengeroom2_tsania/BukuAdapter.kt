package com.example.challengeroom2_tsania

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom2_tsania.room.tbbuku
import kotlinx.android.synthetic.main.adapter_buku.view.*

class BukuAdapter (private val buku : ArrayList<tbbuku>, private val listener: OnAdapterlistener) :
    RecyclerView.Adapter<BukuAdapter.tbbukuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tbbukuViewHolder {
        return tbbukuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_buku, parent, false)
        )
    }

    override fun getItemCount() = buku.size

    override fun onBindViewHolder(holder: tbbukuViewHolder, position: Int) {
        val book =buku[position]
        holder.view.tv_kategori.text =book.kategori
        holder.view.tv_judul.text = book.judul
        holder.view.tv_judul.setOnClickListener{
            listener.onClick(book)
        }
        holder.view.ivEdit.setOnClickListener{
            listener.onUpdate(book)
        }
        holder.view.ivDelete.setOnClickListener {
            listener.onDelete(book)
        }
    }

    class tbbukuViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData (list: List<tbbuku>){
        buku.clear()
        buku.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterlistener{
        fun onClick(tbBook: tbbuku)
        fun onUpdate(tbBook: tbbuku)
        fun onDelete(tbBook: tbbuku)
    }
}

