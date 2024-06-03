package com.example.trashure.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trashure.R
import com.example.trashure.ui.news.Article
import java.text.SimpleDateFormat
import java.util.Locale

class AllAdapter(private val context: Context) : RecyclerView.Adapter<AllAdapter.ViewHolder>() {

    private val articles: ArrayList<Article> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.single_all_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: Article = articles[position]

        // Title
        holder.title.text = article.title

        // Author
        holder.author.text = (article.author ?: context.getString(R.string.unknown)).toString()

        // Date format change
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val inputDate = article.publishedAt
        val outputDate = outputDateFormat.format(inputDateFormat.parse(inputDate)!!)
        holder.date.text = outputDate

        // News image
        Glide.with(context)
            .load(article.urlToImage)
            .placeholder(R.drawable.newsplaceholder)
            .into(holder.imageView)

        // Click listener to open URL in default browser
        holder.itemView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView_singleAllNews)
        val title: TextView = itemView.findViewById(R.id.title_singleAllNews)
        val author: TextView = itemView.findViewById(R.id.author_singleAllNews)
        val date: TextView = itemView.findViewById(R.id.date_singleAllNews)
    }

    fun clear() {
        articles.clear()
        notifyDataSetChanged()
    }

    fun addAll(art: List<Article>) {
        articles.addAll(art)
        notifyDataSetChanged()
    }
}
