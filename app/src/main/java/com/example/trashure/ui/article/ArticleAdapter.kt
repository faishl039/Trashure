package com.example.trashure.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trashure.R

class ArticleAdapter (private val listArticle: ArrayList<Article>): RecyclerView.Adapter<ArticleAdapter.ListViewHolder>() {

    var onItemClick : ((Article) -> Unit)? = null
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgPhoto: ImageView = itemView.findViewById(R.id.image)
        val tvName: TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listArticle.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val film = listArticle[position]
        holder.imgPhoto.setImageResource(film.photo)
        holder.tvName.text = film.name
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listArticle[holder.adapterPosition])
            onItemClick?.invoke(film)
        }

    }

    //ACTIVITY

    interface OnItemClickCallback {
        fun onItemClicked(data: Article)
    }
}