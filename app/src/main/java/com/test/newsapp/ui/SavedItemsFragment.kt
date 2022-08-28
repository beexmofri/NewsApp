package com.test.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.newsapp.R
import com.test.newsapp.adapter.Adapter
import com.test.newsapp.database.NewsDatabase
import com.test.newsapp.data.NewsModel
import com.test.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_items.*

class SavedItemsFragment : Fragment() {

    private lateinit var dbNews: NewsDatabase
    private lateinit var adapter: Adapter
    private lateinit var llm: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_saved_items, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dbNews = NewsDatabase.getSavedItems(requireActivity().applicationContext)
        // gets entity from database
        adapter = Adapter(dbNews.getNewsDao().getAllSavedNews() as MutableList<NewsModel>) { e ->
            onClickCard(
                e
            )
        }
        llm = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        rv_saved.adapter = adapter
        rv_saved.layoutManager = llm
    }

    // navigates to the ItemDetailsFragment
    private fun onClickCard(new : NewsModel) {
        val newsViewModel: NewsViewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        newsViewModel.onNewsModelSelected(new)
        findNavController().navigate(R.id.action_savedItemsFragment_to_itemDetailsFragment) }

}