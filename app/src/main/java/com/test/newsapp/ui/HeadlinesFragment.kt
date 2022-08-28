package com.test.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.newsapp.R
import com.test.newsapp.adapter.Adapter
import com.test.newsapp.data.NewsModel
import com.test.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_headlines.*


class HeadlinesFragment : Fragment() {
    private lateinit var vm: NewsViewModel
    var currentPage = 1
    private lateinit var adapter: Adapter
    lateinit var llm: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_headlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm = ViewModelProvider(this)[NewsViewModel::class.java]
        adapter = Adapter(mutableListOf()) { e -> onClickCard(e) }
        llm = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        rv_news.adapter = adapter
        rv_news.layoutManager = llm
        getPopularNews()
    }

    fun getPopularNews() {
        vm.fetchHeadlines(currentPage)
        vm.getOnSuccess().observe(requireActivity()) { t ->
            if (!t!!)
                onError()
        }
        vm.mutableNewsList.observe(requireActivity()
        ) { list ->
            adapter.appendNews(list as ArrayList<NewsModel>)
            attachOnClickListener()
        }
    }

    private fun onClickCard(new: NewsModel) {
        val newsViewModel: NewsViewModel =
            ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        newsViewModel.onNewsModelSelected(new)
        findNavController().navigate(
            R.id.action_headlinesFragment_to_itemDetailsFragment
        )
    }

    private fun onError() {
        Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show()
    }

    private fun attachOnClickListener() {
        rv_news.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItems = llm.itemCount
                val visibleItemsCount = llm.childCount
                val firstVisibleItem = llm.findLastVisibleItemPosition()

                if (firstVisibleItem + visibleItemsCount >= totalItems / 2) {
                    rv_news.removeOnScrollListener(this)
                    currentPage++
                    getPopularNews()
                }
            }
        })
    }
}