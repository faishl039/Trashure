package com.example.trashure.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trashure.R
import com.example.trashure.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private lateinit var rvDetail: RecyclerView
    private val list = ArrayList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvDetail = view.findViewById(R.id.rvArticle)
        rvDetail.setHasFixedSize(true)

        list.addAll(getListArticle())
        showRecyclerList()
    }

    private fun getListArticle(): ArrayList<Article> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription1 = resources.getStringArray(R.array.data_description1)
        val dataDescription2 = resources.getStringArray(R.array.data_description2)
        val dataDescription3 = resources.getStringArray(R.array.data_description3)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listArticle = ArrayList<Article>()
        for (i in dataName.indices) {
            val article = Article(dataName[i], dataDescription1[i],dataDescription2[i], dataDescription3[i], dataPhoto.getResourceId(i, -1))
            listArticle.add(article)
        }
        dataPhoto.recycle() // Make sure to recycle TypedArray after use
        return listArticle
    }

    private fun showRecyclerList() {
        rvDetail.layoutManager = LinearLayoutManager(requireContext())
        val listArticleAdapter = ArticleAdapter(list)
        rvDetail.adapter = listArticleAdapter

        listArticleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Article) {
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(article: Article) {
        val intent = Intent(requireContext(), DetailArticleActivity::class.java)
        intent.putExtra("Article", article) // Send the selected Film object to DetailFilm Activity
        startActivity(intent)
    }
}
