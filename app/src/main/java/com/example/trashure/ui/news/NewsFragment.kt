package com.example.trashure.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trashure.R
import com.example.trashure.databinding.FragmentNewsBinding

class NewsFragment : Fragment(), NewsAdapter.OnItemClickListener {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val news = listOf(
            NewsData("news 1", R.drawable.botolplastik),
            NewsData("news 2", R.drawable.botolplastik),
            NewsData("news 3", R.drawable.botolplastik),
            NewsData("news 4", R.drawable.botolplastik),
            NewsData("news 5", R.drawable.botolplastik),
        )

        val adapter = NewsAdapter(this)
        adapter.submitList(news)
        binding.rvNews.layoutManager = LinearLayoutManager(context)
        binding.rvNews.adapter = adapter
    }

    override fun onItemClick(news: NewsData) {

    }

}