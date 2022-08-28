package com.test.newsapp.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.test.newsapp.R
import com.test.newsapp.database.NewsDatabase
import com.test.newsapp.data.NewsModel
import com.test.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_item_details.*

class ItemDetailsFragment : Fragment() {

    private lateinit var nModel: NewsModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_item_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val dbNews: NewsDatabase = NewsDatabase.getSavedItems(requireActivity().applicationContext)
        val fmodel: NewsViewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        fmodel.getSelectedNewsModel().observe(requireActivity()
        ) {
            nModel = it
        }

        super.onViewCreated(view, savedInstanceState)
        addData()

        readFull.setOnClickListener {
            findNavController().navigate(R.id.action_itemDetailsFragment_to_webFragment3)
        }

        if (dbNews.getNewsDao().getCount(nModel.title) == 1)
            bookmark_icon.setColorFilter(Color.parseColor("#6200EE"))

        bookmark_icon.setOnClickListener {
            if (dbNews.getNewsDao().getCount(nModel.title) == 0) {
                dbNews.getNewsDao().insertNews(nModel)
                bookmark_icon.setColorFilter(Color.parseColor("#6200EE"))
                Toast.makeText(context, "News Saved", Toast.LENGTH_SHORT).show()
            } else {
                dbNews.getNewsDao().delete(nModel)
                bookmark_icon.setColorFilter(Color.parseColor("#484a49"))
                Toast.makeText(context, "News Unsaved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addData() {
        date_time.text = nModel.publishedAt
        des.text = nModel.desciption
        website2.text = nModel.source.name
        articleTitle2.text = nModel.title
        val image = nModel.urlToImage
        Glide.with(requireActivity())
            .load(image)
            .transform(CenterCrop())
            .into(imageView3)
    }
}
