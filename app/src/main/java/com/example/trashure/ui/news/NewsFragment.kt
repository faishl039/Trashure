package com.example.trashure.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.trashure.R
import com.example.trashure.adapter.AllAdapter
import com.example.trashure.adapter.HeadlinesAdapter
import com.example.trashure.network.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NewsFragment : Fragment() {

    private lateinit var headlineRV: RecyclerView
    private lateinit var allNewsRV: RecyclerView

    private lateinit var headlinesAdapter: HeadlinesAdapter
    private lateinit var allAdapter: AllAdapter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBarActivityMain: ProgressBar

    private lateinit var breakingNewsLayout: LinearLayout
    private lateinit var allNewsLayout: LinearLayout

    private lateinit var allNewsLayoutManager: LinearLayoutManager

    var pageNum = 1
    var totalAllNews = -1

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        headlineRV = view.findViewById(R.id.terkinirv)
        allNewsRV = view.findViewById(R.id.semuaBeritarv)
        swipeRefreshLayout = view.findViewById(R.id.swipeContainer_ActivityMain)
        breakingNewsLayout = view.findViewById(R.id.terkiniLayout)
        allNewsLayout = view.findViewById(R.id.semuaBeritaLayout)
        progressBarActivityMain = view.findViewById(R.id.progressBar_ActivityMain)

        allNewsLayoutManager = LinearLayoutManager(context)

        hideAll()
        getAllNews()
        getHeadLines()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getHeadLines()
            getAllNews()
        }

        allNewsRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (totalAllNews > allNewsLayoutManager.itemCount && allNewsLayoutManager.findFirstVisibleItemPosition() >= allNewsLayoutManager.itemCount - 1) {
                    pageNum++
                    getAllNews()
                }
            }
        })
    }

    private fun showAll() {
        progressBarActivityMain.visibility = View.INVISIBLE
        breakingNewsLayout.visibility = View.VISIBLE
        allNewsLayout.visibility = View.VISIBLE
    }

    private fun hideAll() {
        breakingNewsLayout.visibility = View.INVISIBLE
        allNewsLayout.visibility = View.INVISIBLE
        progressBarActivityMain.visibility = View.VISIBLE
    }

    private fun getAllNews() {
        hideAll()
        val news = NewsService.newsInstance.geteverything("recycle", pageNum)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val allNews = response.body()
                if (allNews != null) {
                    totalAllNews = allNews.totalResults
                    if (!::allAdapter.isInitialized) {
                        allAdapter = AllAdapter(requireContext())
                        allNewsRV.adapter = allAdapter
                        allNewsRV.layoutManager = allNewsLayoutManager
                    }
                    if (pageNum == 1) {
                        allAdapter.clear()
                    }
                    allAdapter.addAll(allNews.articles)
                    showAll()
                } else {
                    showAll() // Show all even if there's no new data
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(TAG, "Failed Fetching News", t)
                showAll() // Show all even on failure
            }
        })
    }

    private fun getHeadLines() {
        hideAll()
        val news = NewsService.newsInstance.getheadlines("trash", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val headNews = response.body()
                if (headNews != null) {
                    headlinesAdapter = HeadlinesAdapter(requireContext())
                    headlinesAdapter.clear()
                    headlinesAdapter.addAll(headNews.articles)
                    headlineRV.adapter = headlinesAdapter
                    headlineRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    showAll()
                } else {
                    showAll() // Show all even if there's no new data
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(TAG, "Failed Fetching News", t)
                showAll() // Show all even on failure
            }
        })
    }

    companion object {
        const val TAG = "client"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
