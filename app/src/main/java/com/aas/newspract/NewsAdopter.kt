package com.aas.newspract

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdopter(private val listner : NewsItemClicked): RecyclerView.Adapter<ViewHolder>() {

    private val item: ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewholder = ViewHolder(view)

        view.setOnClickListener{
            listner.onItemClicked(item[viewholder.adapterPosition])
        }
        return viewholder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = item[position]

        holder.titilView.text = currentItem.title
        holder.author.text = "${currentItem.author}."
        holder.timeAgo.text = currentItem.time
        Glide.with(holder.itemView.context ).load(currentItem.imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun updateNews(updatedNews : ArrayList<News>){

        item.clear()
        item.addAll(updatedNews)
        notifyDataSetChanged()

    }
}

class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val titilView : TextView = itemView.findViewById(R.id.title)
    val imageView : ImageView = itemView.findViewById(R.id.imageView)
    val author :TextView = itemView.findViewById(R.id.author)
    val timeAgo:TextView= itemView.findViewById(R.id.news_time)
}

interface NewsItemClicked{

    fun onItemClicked(item : News)
}