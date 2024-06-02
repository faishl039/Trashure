package com.example.trashure.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trashure.databinding.ItemArticleBinding

class NewsAdapter(private val listener: OnItemClickListener) :
    ListAdapter<NewsData, NewsAdapter.ArticleViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
        holder.itemView.setOnClickListener {
            listener.onItemClick(news)
        }
    }

    class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: NewsData) {
            binding.title.text = news.title
            binding.image.setImageResource(news.image)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(news: NewsData)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsData>() {
            override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
                return oldItem.title == newItem.title // assuming title is unique
            }

            override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
                return oldItem == newItem
            }
        }
    }
}