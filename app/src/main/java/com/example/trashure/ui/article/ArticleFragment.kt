package com.example.trashure.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trashure.R
import com.example.trashure.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(), ArticleAdapter.OnItemClickListener {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articles = listOf(
            ArticleData("Article 1", R.drawable.botolplastik),
            ArticleData("Article 2", R.drawable.botolplastik),
            ArticleData("Article 3", R.drawable.botolplastik),
            ArticleData("Article 4", R.drawable.botolplastik),
            ArticleData("Article 5", R.drawable.botolplastik),
            ArticleData("Article 6", R.drawable.botolplastik),
        )

        val adapter = ArticleAdapter(this)
        adapter.submitList(articles)
        binding.rvArticle.layoutManager = LinearLayoutManager(context)
        binding.rvArticle.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(article: ArticleData) {
//        val action = ArticleFragmentDirections.actionArticleListFragmentToArticleDetailFragment(
//            article.title, article.image
//        )
//        findNavController().navigate(action)
    }
}